/*document.addEventListener("DOMContentLoaded", function() {
    fetch('/api/info')
        .then(response => response.json())
        .then(data => {
            document.getElementById("info-box").textContent = data.text;
        })
        .catch(error => {
            document.getElementById("info-box").textContent = "Ошибка загрузки данных";
            console.error('Ошибка:', error);
        });
});
Код сверху для бэков */ 

document.addEventListener("DOMContentLoaded", function() {
    fetch('info.txt')
        .then(response => response.text())
        .then(data => {
            // Используем innerHTML и стили для сохранения форматирования
            document.getElementById("info-box").innerHTML = data.replace(/\n/g, '<br>');
        })
        .catch(error => {
            document.getElementById("info-box").textContent = "Ошибка загрузки данных";
            console.error('Ошибка:', error);
        });
});
const flag = localStorage.getItem('flag');
if (flag == 1){
    document.getElementById('b1').innerHTML = '<img src="images/pop.gif" alt="Картинка" id="my-image">';
}
