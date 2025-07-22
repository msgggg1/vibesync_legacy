<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
         <!-- banner -->
          <div class="slider-container">
            <div class="swiper" id="swiper1">
              <div class="swiper-wrapper">
                <div class="swiper-slide">Card 1</div>
                <div class="swiper-slide">Card 2</div>
                <div class="swiper-slide">Card 3</div>
                <div class="swiper-slide">Card 4</div>
                <div class="swiper-slide">Card 5</div>
              </div>
              <div class="swiper-button-prev" id="prev" style="background-color: var(--border-color); border-radius: 6px;"></div>
              <div class="swiper-button-next" id="next" style="background-color: var(--border-color); border-radius: 6px;"></div>
            </div>
          </div>
  
          <!-- category btn -->
          <div class="category_btn_group">
            <c:forEach items="${ categoryList }" var="categoryVO">
               <c:if test="${categoryVO.categoryIdx != categoryIdx}">
                  <button style="background-image: url( ${pageContext.request.contextPath}/${ categoryVO.img }); background-size: cover;"
                        onclick="location.href='board/list?categoryIdx=${categoryVO.categoryIdx}'">
                     <p>${ categoryVO.categoryName }</p>
                  </button>
               </c:if>
            </c:forEach>
          </div>
  
          <!-- grid -->
          <div class="grid_wrapper">
            <div class="grid_item" id="recent_posts_container" >
              <p class="grid_header">Recent_Posts</p>
               <c:forEach items="${mainPageDTO.latestNotes}" var="post" varStatus="status">
                  <div class="list-entry" data-id="${post.noteIdx}">
                     <a href="postView.do?nidx=${post.noteIdx}" >
                         <span class="entry-number">${status.count}.</span><span class="entry-title">${post.title}</span>
                     </a>
                  </div>
              </c:forEach>
            </div>
            <div class="grid_item" id="popular_posts_container">
              <p class="grid_header">Popular_Posts</p>
                <c:forEach items="${mainPageDTO.popularNotes}" var="post" varStatus="status">
                  <div class="list-entry" data-id="${post.noteIdx}">
                      <a href="postView.do?nidx=${ post.noteIdx }">
                         <span class="entry-number">${status.count}.</span><span class="entry-title">${post.title}</span>
                     </a>
                  </div>
              </c:forEach>
            </div>
            <div class="grid_item" id="popular_users_container">
              <p class="grid_header">Top_User</p>
                 <c:forEach items="${mainPageDTO.popularUsers}" var="user" varStatus="status">
                  <div class="list-entry" data-id="${status.count}">
                     <a href="userPage.do?acIdx=${ user.acIdx }" >
                         <span class="entry-number">${status.count}</span>
                         <span class="entry-title">${user.nickname}</span>
                     </a>
                  </div>
               </c:forEach>
            </div>
          </div>

          <!-- other category -->
          <div class="slider-container">
            <p class="top_banner">Other_Posts</p>
            <div class="swiper" id="swiper2">
              <div class="swiper-wrapper">
                 <c:forEach items="${ mainPageDTO.popularNotesNotByMyCategory }" var="posts">
                    <div class="swiper-slide">
                    <ul class="under_ul">
                    <c:forEach items="${ posts.value }" var="post">
                       <li>
                       <a href="postView.do?nidx=${ post.noteIdx }">
                          <div class="post-index" style="display: inline-block; align-self: left;">${ post.noteIdx }</div>
                          <div class="post-title" style="display: inline-block; align-self: right;">${ post.title }</div>
                       </a>
                       </li>
                    </c:forEach>
                    </ul>
                    </div>
                 </c:forEach>
              </div>
              <div class="swiper-button-prev" id="prev2" style="background: linear-gradient(90deg, rgba(138, 196, 255, 1) 0%, rgba(227, 176, 255, 1) 50%, rgba(165, 250, 120, 1) 100%); border-radius: 20px;"></div>
              <div class="swiper-button-next" id="next2" style="background: linear-gradient(90deg, rgba(138, 196, 255, 1) 0%, rgba(227, 176, 255, 1) 50%, rgba(165, 250, 120, 1) 100%); border-radius: 20px;"></div>
            </div>
          </div>
  
  
        </section>