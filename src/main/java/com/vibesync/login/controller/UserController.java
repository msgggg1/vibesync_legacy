package com.vibesync.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vibesync.login.domain.SignUpDTO;
import com.vibesync.login.service.UserService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UserController { 

	@Autowired
	private UserService	userService;
	
	@GetMapping("/login")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("/user/signUp")
	public String signUp(SignUpDTO signUpDTO, 
										HttpServletRequest request,
										HttpServletResponse response,
										RedirectAttributes rttr){
		try {
			userService.register(signUpDTO);
			request.setAttribute("signupSuccessForDisplay", "회원가입이 성공적으로 완료되었습니다.");	
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("prevSignupName", signUpDTO.getName());
            request.setAttribute("prevSignupNickname", signUpDTO.getNickname());
            request.setAttribute("prevSignupEmail", signUpDTO.getEmail());
            request.setAttribute("formToShow", "signUp");
            request.setAttribute("signupErrorForDisplay", e.getMessage());
		}
		return "redirect:/login";
	}
	
	/*
	@GetMapping
    public ResponseEntity<List<DeptVO>> getList() {
        List<DeptVO> list = deptMapper.getDeptList();

        if (list == null || list.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.ok(list); // 200 OK with JSON body
        }
    }
    */
	
	/*
	 * @PostMapping public ResponseEntity<Integer> insertDept(@RequestBody DeptVO
	 * deptVO) { int rowcount = this.deptMapper.insertDept(deptVO);
	 * 
	 * if (rowcount == 0) { return
	 * ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(rowcount); } else { return
	 * ResponseEntity.status(HttpStatus.OK).body(rowcount); } }
	 */
	
	/*
	// web.xml의 시큐리티 필터 주석처리
	@PostMapping("")
	   public int insert(@RequestBody DeptVO deptVO) {
	      return this.deptMapper.insertDept(deptVO);
	   }
	
	@PutMapping("/{deptno}")
	   public ResponseEntity<String> update(@PathVariable("deptno") int  deptno, @RequestBody DeptVO deptVO) {
	      deptVO.setDeptno(deptno);
	      int result = this.deptMapper.updateDept(deptVO);
	      if (result > 0) {
	           return ResponseEntity.ok("부서 정보가 성공적으로 수정되었습니다.");
	       } else {
	           return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                .body("해당 부서번호(" + deptno + ")의 부서를 찾을 수 없습니다.");
	       }
	   }
	   */
	   /* 
	   PUT /dept/50
	   Content-Type: application/json

	   {
	     "dname": "SALES",
	     "loc": "SEOUL"
	   }
	   */
	   
	   /*
	   @DeleteMapping("/{deptno}")
	    public ResponseEntity<String> delete(@PathVariable int deptno) {
	        int result = deptMapper.deleteDept(deptno);
	        if (result > 0) {
	            return ResponseEntity.ok("부서 삭제 성공");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 부서를 찾을 수 없습니다");
	        }
	    }

	   
	   @GetMapping("/{deptno}")
	   public ResponseEntity<DeptVO> getDept(@PathVariable("deptno") int deptno) {
	       DeptVO dept = deptMapper.getDept(deptno);
	       if (dept != null) {
	           return ResponseEntity.ok(dept);
	       } else {
	           return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                .body(null);
	       }
	   }
	   */

}
