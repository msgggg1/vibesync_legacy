document.addEventListener('DOMContentLoaded', () => {
  const back_btn = document.querySelector('.back_icon');
  if (back_btn != null) {
    back_btn.addEventListener('click', () => {
      window.history.back();
    })
  }

  const sidebar = document.getElementById('sidebar');
  if (sidebar != null) {
    const btn = document.getElementById('toggle-btn');
    sidebar.classList.add('collapsed');
    btn.textContent = '✖';

    function checkWidth() {
      if (window.innerWidth <= 1000) {
        btn.style.display = 'block';
        btn.style.position = 'fixed';
        // collapsed 상태이면 content margin 0
        if (sidebar.classList.contains('collapsed')) {
          btn.textContent = '☰';
        }
      } else {
        btn.style.display = 'none';
        sidebar.classList.remove('collapsed');
        btn.textContent = '✖';
      }
    }

    btn.addEventListener('click', () => {
      sidebar.classList.toggle('collapsed');
      // toggle에 따라 content margin 동기화
      const content = document.getElementById('content');
      if (sidebar.classList.contains('collapsed')) {
        btn.textContent = '☰';
      } else {
        btn.textContent = '✖';
      }
    });

    window.addEventListener('resize', checkWidth);
    checkWidth();
  }
  
});
