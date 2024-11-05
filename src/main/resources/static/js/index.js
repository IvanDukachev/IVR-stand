var flag;
const button = document.getElementById('Zhest');
// Функция, которая будет вызвана при клике на кнопку
button.addEventListener('click', function() {
    // При клике на кнопку присваиваем переменной новое значение
    flag = 1;
    localStorage.setItem('flag', flag);
    console.log(flag); // Выводим новое значение в консоль
});
const baton = document.getElementById('Text');
// Функция, которая будет вызвана при клике на кнопку
baton.addEventListener('click', function() {
    // При клике на кнопку присваиваем переменной новое значение
    flag = 0;
    localStorage.setItem('flag', flag);
    console.log(flag); // Выводим новое значение в консоль
});