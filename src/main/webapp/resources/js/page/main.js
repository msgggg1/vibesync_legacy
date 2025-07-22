
    const swiper1 = new Swiper('#swiper1', {
      loop: true,
      slidesPerView: 'auto',
      centeredSlides: true,
      spaceBetween: 0,
      autoplay: true,
      navigation: {
        nextEl: '#next',
        prevEl: '#prev',
      }
    });
  
    const swiper2 = new Swiper('#swiper2', {
      loop: false,
      navigation: {
        nextEl: '#next2',
        prevEl: '#prev2',
      },
      breakpoints: {
        0: {
          slidesPerView: 1,
          spaceBetween: 10,
          centeredSlides: true
        },
        600: {
          slidesPerView: 3,
          spaceBetween: 10,
          centeredSlides: false
        }
      },
      on: {
        init: function () {
          updateNavButtons(this);
        },
        slideChange: function () {
          updateNavButtons(this);
        },
      },
    });
  
    function updateNavButtons(swiper) {
      const prevBtn = document.querySelector('#prev2');
      const nextBtn = document.querySelector('#next2');
      if (swiper.activeIndex === 0) {
        prevBtn.classList.add('swiper-button-disabled');
      } else {
        prevBtn.classList.remove('swiper-button-disabled');
      }
      if (swiper.activeIndex >= swiper.slides.length - swiper.params.slidesPerView) {
        nextBtn.classList.add('swiper-button-disabled');
      } else {
        nextBtn.classList.remove('swiper-button-disabled');
      }
    }
    
    function initRotatingHighlight(containerId, interval = 2800) {
            console.log(`Initializing rotating highlight for ${containerId}`);
            const container = document.getElementById(containerId);
            if (!container) {
                console.warn(`Container with ID ${containerId} not found.`);
                return;
            }
            const items = container.querySelectorAll('.list-entry');
            if (items.length === 0) {
                return;
            }

            let currentIndex = Math.floor(Math.random() * items.length);

            function resetItems() {
                items.forEach(item => {
                    item.classList.remove('active-highlight');
                });
            }

            function highlightItem(index) {
                if (items[index]) {
                    items[index].classList.add('active-highlight');
                }
            }

            resetItems();
            highlightItem(currentIndex);
            console.log(`Initial highlight for ${containerId}: item index ${currentIndex}`);

            setInterval(() => {
                resetItems();
                currentIndex = (currentIndex + 1) % items.length;
                highlightItem(currentIndex);
            }, interval);
        }
    
        initRotatingHighlight('recent_posts_container', 2800);
        initRotatingHighlight('popular_posts_container', 2800);
        initRotatingHighlight('popular_users_container', 2800);
