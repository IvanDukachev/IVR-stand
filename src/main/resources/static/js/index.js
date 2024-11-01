document.querySelectorAll('.button').forEach(button => {
    button.addEventListener('click', () => {
      const flag = button.dataset.flag;
      localStorage.setItem('flag', flag);
      console.log(`Flag set to ${flag}`);
    });
  });
  