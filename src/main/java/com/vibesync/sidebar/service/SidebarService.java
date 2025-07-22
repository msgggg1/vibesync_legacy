package com.vibesync.sidebar.service;

import org.springframework.transaction.annotation.Transactional;

import com.vibesync.security.domain.CustomUser;
import com.vibesync.sidebar.domain.SidebarDTO;

public interface SidebarService {

    @Transactional(readOnly = true)
    public SidebarDTO loadSidebar(CustomUser customUser);

}
