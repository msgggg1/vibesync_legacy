package com.vibesync.member.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vibesync.listener.DuplicateLoginPreventer;
import com.vibesync.member.domain.LoginDTO;
import com.vibesync.member.domain.MemberVO;
import com.vibesync.member.domain.SignUpDTO;
import com.vibesync.member.service.MemberService;
import com.vibesync.member.util.Config;


/**
 * 사용자 인증(로그인, 로그아웃, 회원가입), 비밀번호 재설정 등
 * 사용자 관련 모든 웹 요청을 처리하는 컨트롤러.
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService; 

    @GetMapping("/login")
    public String showLoginForm(HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model,
                                @CookieValue(value = "rememberedEmail", required = false) String rememberedEmail,
                                @CookieValue(value = "autoLoginUserEmail", required = false) String autoLoginEmail
                                ,@RequestParam(value = "from", required = false) String from) throws IOException {
        
        // 캐시 제어 헤더 설정
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // 2. 자동 로그인 쿠키 확인
        if (!"logout".equals(from)&&autoLoginEmail != null) {
            MemberVO memberInfo = null;
			try {
				memberInfo = memberService.autoLogin(autoLoginEmail);
			} catch (Exception e) {
				e.printStackTrace();
			}
            if (memberInfo != null) {
                // 자동 로그인 성공 시, 공통 로그인 처리 후 리다이렉트
                return processSuccessfulLogin(request, response, session, memberInfo, false, false);
            }
        }

        // 3. 이메일 기억하기 쿠키가 있으면 모델에 추가
        if (rememberedEmail != null) {
            model.addAttribute("rememberedEmail", rememberedEmail);
        }

        // 4. Google Client ID를 모델에 추가
        model.addAttribute("googleClientId", Config.getGoogleClientId());

        return "login"; 
    }
    
    
    @PostMapping("/login")
    public String processLogin() {
        return null;
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute SignUpDTO signUpDTO, RedirectAttributes redirectAttributes) {
        try {
        	memberService.register(signUpDTO);
            redirectAttributes.addFlashAttribute("signupSuccessForDisplay", "회원가입이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            // 회원가입 실패 시, 입력했던 데이터를 다시 폼에 보여주기 위해 Flash Attribute로 전달
            redirectAttributes.addFlashAttribute("prevSignupName", signUpDTO.getName());
            redirectAttributes.addFlashAttribute("prevSignupNickname", signUpDTO.getNickname());
            redirectAttributes.addFlashAttribute("prevSignupEmail", signUpDTO.getEmail());
            redirectAttributes.addFlashAttribute("formToShow", "signUp"); 
            redirectAttributes.addFlashAttribute("signupErrorForDisplay", e.getMessage());
        }
        return "redirect:/member/login";
    }
    
    @PostMapping("/complete-google-signup")
    public String processCompleteGoogleSignUp(@RequestParam("nickname") String nickname,
                                              @RequestParam("category_idx") int categoryIdx,
                                              HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                              RedirectAttributes redirectAttributes) throws IOException {
        SignUpDTO newGoogleUser = (SignUpDTO) session.getAttribute("newGoogleUser");

        if (newGoogleUser == null) {
            redirectAttributes.addFlashAttribute("loginErrorForDisplay", "인증 정보가 만료되었습니다. 다시 시도해주세요.");
            return "redirect:/member/login";
        }

        newGoogleUser.setNickname(nickname);
        newGoogleUser.setCategory_idx(categoryIdx);

        try {
        	memberService.register(newGoogleUser);
            session.removeAttribute("newGoogleUser");
            
            MemberVO newMemberInfo = memberService.getUserByEmail(newGoogleUser.getEmail());
            if (newMemberInfo != null) {
                System.out.println("Google 신규 회원 가입 성공, 즉시 로그인");
                // 구글 회원가입 성공 시, 자동 로그인을 위해 쿠키는 true로 설정
                return processSuccessfulLogin(request, response, session, newMemberInfo, true, true);
            } else {
                redirectAttributes.addFlashAttribute("loginErrorForDisplay", "가입 처리 중 오류가 발생했습니다. 다시 로그인해주세요.");
                return "redirect:/member/login";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("formToShow", "googleExtraInfo");
            redirectAttributes.addFlashAttribute("signupErrorForDisplay", e.getMessage());
            // 에러 발생 시, newGoogleUser 정보를 다시 세션에 저장하여 폼을 다시 보여줄 수 있도록 함
            session.setAttribute("newGoogleUser", newGoogleUser); 
            return "redirect:/member/login";
        }
    }

    @PostMapping("/request-password-reset")
    public String processRequestPasswordReset(@RequestParam("email") String email, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String requestURL = request.getRequestURL().toString();
        String contextPath = request.getContextPath();
        String baseURL = requestURL.substring(0, requestURL.indexOf(contextPath) + contextPath.length());

        try {
        	memberService.initiateReset(email, baseURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 이메일 존재 여부와 상관없이 동일한 메시지를 보여 보안 강화
        redirectAttributes.addFlashAttribute("loginMessage", "입력하신 이메일로 계정이 존재할 경우, 비밀번호 재설정 링크를 전송했습니다.");
        return "redirect:/member/login";
    }

    @GetMapping("/show-reset-form")
    public String showResetForm(@RequestParam("token") String token, Model model, RedirectAttributes redirectAttributes) {
        if (token == null || token.isEmpty()) {
            redirectAttributes.addFlashAttribute("loginErrorForDisplay", "유효하지 않거나 만료된 링크입니다. 다시 시도해주세요.");
            return "redirect:/member/login";
        }
        model.addAttribute("token", token);
        return "resetPasswordForm"; // /WEB-INF/views/resetPasswordForm.jsp
    }

    @PostMapping("/perform-password-reset")
    public String processPerformPasswordReset(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {
        try {
            boolean success = memberService.finalizeReset(token, newPassword);
            if (success) {
                redirectAttributes.addFlashAttribute("signupSuccessForDisplay", "비밀번호가 성공적으로 재설정되었습니다. 다시 로그인해주세요.");
            } else {
                redirectAttributes.addFlashAttribute("loginErrorForDisplay", "유효하지 않거나 만료된 링크입니다. 다시 시도해주세요.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("loginErrorForDisplay", "비밀번호 재설정 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
        return "redirect:/member/login";
    }


    // ===================================================================================
    // Private Helper Methods
    // ===================================================================================

    /**
     * 로그인 성공 시 공통 로직을 처리하는 헬퍼 메소드.
     * (중복 로그인 방지, 쿠키 설정, 세션 설정, 페이지 리다이렉트)
     */
    private String processSuccessfulLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                          MemberVO memberInfo, boolean rememberEmail, boolean autoLogin) throws IOException {
        String memberEmail = memberInfo.getEmail();

        // 1. 중복 로그인 방지 로직
        if (DuplicateLoginPreventer.loginUsers.containsKey(memberEmail)) {
            HttpSession oldSession = DuplicateLoginPreventer.loginUsers.get(memberEmail);
            if (oldSession != null && !oldSession.getId().equals(session.getId())) {
                System.out.println("[UserController] 중복 로그인 감지! 기존 세션 강제 종료: " + memberEmail);
                try {
                    oldSession.invalidate(); // 기존 세션 무효화
                } catch (IllegalStateException e) {
                    System.err.println("이미 무효화된 세션에 접근: " + e.getMessage());
                }
            }
        }
        DuplicateLoginPreventer.loginUsers.put(memberEmail, session);

        // 2. '이메일 기억하기', '자동 로그인' 쿠키 처리
        handleLoginCookies(response, memberEmail, rememberEmail, autoLogin);

        // 3. 사용자 정보를 세션에 저장
        session.setAttribute("memberInfo", memberInfo);
        //session.setAttribute("theme", settingService.getTheme(memberInfo.getAc_idx()));
        
        // 4. 사용자 ac_idx 쿠키 저장 (클라이언트 측에서 활용)
        Cookie userIdxCookie = new Cookie("login_user_idx", String.valueOf(memberInfo.getAc_idx()));
        userIdxCookie.setMaxAge(60 * 60 * 24 * 7); // 7일
        userIdxCookie.setPath("/");
        response.addCookie(userIdxCookie);

        // 5. 이전 페이지 또는 메인 페이지로 리다이렉트
        return redirectToPreviousOrMainPage(session, request);
    }

    private void handleLoginCookies(HttpServletResponse response, String userEmail, boolean rememberEmail, boolean autoLogin) {
        // '이메일 기억하기' 처리
        Cookie emailCookie = new Cookie("rememberedEmail", rememberEmail ? userEmail : null);
        emailCookie.setPath("/");
        emailCookie.setMaxAge(rememberEmail ? 60 * 60 * 24 * 30 : 0); // 30일 또는 즉시 삭제
        response.addCookie(emailCookie);

        // '자동 로그인' 처리
        if (autoLogin) {
            Cookie autoLoginCookie = new Cookie("autoLoginUserEmail", userEmail);
            autoLoginCookie.setPath("/");
            autoLoginCookie.setMaxAge(60 * 60 * 24 * 30); // 30일
            autoLoginCookie.setHttpOnly(true); // JavaScript 접근 방지
            response.addCookie(autoLoginCookie);
        }
    }
    
    private String redirectToPreviousOrMainPage(HttpSession session, HttpServletRequest request) {
        String referer = (String) session.getAttribute("referer");
        session.removeAttribute("referer"); // 사용 후 즉시 제거

        if (referer != null && !referer.isEmpty() && !referer.contains("/member/login")) {
            return "redirect:" + referer;
        } else {
            return "redirect:/page/main";
        }
    }
}
