package com.vibesync.common.tiles;

import java.net.MalformedURLException;

import javax.servlet.ServletContext;

import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.apache.tiles.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component("pageAssetPreparer")
@Log4j
public class PageAssetPreparer implements ViewPreparer {

    @Autowired
    private ServletContext servletContext;

    @Override
    public void execute(Request tilesRequest, AttributeContext attributeContext) {
    	log.info("===== PageAssetPreparer execute() 시작 =====");
    	
    	Attribute bodyAttr = attributeContext.getAttribute("body");
    	
        if (bodyAttr != null && bodyAttr.getValue() != null) {
            String bodyPath = bodyAttr.getValue().toString();
            
            String viewName = bodyPath.replace("/WEB-INF/views/", "").replace(".jsp", "");
            String headFragmentPath = "/WEB-INF/views/" + viewName + "_head.jsp";
            String cssPath = "/resources/css/" + viewName + ".css";

            log.info("Generated headFragmentPath: " + headFragmentPath);
            log.info("Generated cssPath: " + cssPath);
            
            // 실제로 그 경로에 파일이 존재하는지 확인
            try {
            	// getResource 메서드를 활용해 파일이 존재할 경우에만 request scope에 경로를 담음
            	
				// head 태그 안 프레임워크/라이브러리
            	if (servletContext.getResource(headFragmentPath) != null) {
            		log.info(">>> Fragment Found!: " + headFragmentPath);
            		attributeContext.putAttribute( "pageHead", new Attribute(headFragmentPath));
				} else {
					log.info(">>> Fragment Not Found: " + headFragmentPath);
				}
            	
				// css
				if (servletContext.getResource(cssPath) != null) {
					log.info(">>> CSS Found!: " + cssPath);
					tilesRequest.getContext("request").put("pageCss", cssPath);
				} else {
					log.info(">>> CSS Not Found: " + cssPath); }
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
        }
        
        log.info("===== PageAssetPreparer execute() 종료 =====");
    }
}