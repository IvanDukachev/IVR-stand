const noCopyElements = document.querySelectorAll('.no-copy');
noCopyElements.forEach((element) => {
  element.addEventListener('copy', (event) => {
    event.preventDefault();
    return false;
  });
});