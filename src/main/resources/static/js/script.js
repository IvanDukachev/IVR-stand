const flag = localStorage.getItem('flag');
console.log(flag)
if (flag == 1) {
    document.getElementById('b1').innerHTML = '<img src="images/pop.gif" alt="Картинка" id="my-image"><img src="images/1usluga.png" draggable="false" alt="Картинка"><span class="no-copy">Пенсии и иные выплаты</span>';
    document.getElementById('b2').innerHTML = '<img src="images/pop.gif" alt="Картинка" id="my-image"><img src="images/lgota.png" alt="Картинка"><span class="no-copy">Федеральным льготникам</span>'
    document.getElementById('b3').innerHTML = '<img src="images/pop.gif" alt="Картинка" id="my-image"><img src="images/prochee.png" alt="Картинка"><span class="no-copy">Прочие услуги</span>'
  } else {
    document.getElementById('b1').innerHTML = '<img src="images/1usluga.png" draggable="false" alt="Картинка"><span class="no-copy">Пенсии и иные выплаты</span>';
    document.getElementById('b2').innerHTML = '<img src="images/lgota.png" alt="Картинка"><span class="no-copy">Федеральным льготникам</span>'
    document.getElementById('b3').innerHTML = '<img src="images/prochee.png" alt="Картинка"><span class="no-copy">Прочие услуги</span>'
  }
var form = document.getElementById('myForm');
var userInput = document.getElementById('userInput');
var submitButton = document.getElementById('submitButton');

submitButton.addEventListener('click', (e) => {
  e.preventDefault(); // предотвращаем отправку формы по умолчанию

  const userInputValue = userInput.value; // получаем значение input и удаляем пробелы

  if (userInputValue!== '') {
    var jsonData = { text: userInputValue }; // создаем объект с ключом "text" и значением userInputValue
    var json = JSON.stringify(jsonData); // преобразуем объект в JSON-строку
    console.log(json); // выводим JSON-строку в консоль
    fetch('http://localhost:5500/search', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      },
      body: json
    })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error(error));
  } else {
    console.log('Пустое поле');
  } 
});