  document.querySelector('input[name="cpf"]').addEventListener('input', function(e) {
    this.value = this.value.replace(/\D/g, '')
  });
  
        const toggle = document.querySelector(".menu-toggle");
    const nav = document.querySelector(".nav");
    toggle.addEventListener("click", () => {
        nav.classList.toggle("open");
    });

     const header = document.querySelector('.header');
    let defaultWidth = window.innerWidth;

    window.addEventListener('resize', () => {
        const currentWidth = window.innerWidth;
        const scale = currentWidth / defaultWidth;

        if (scale < 0.80) {
            header.style.display = 'none';
        } else {
            header.style.display = '';
        }
    });
