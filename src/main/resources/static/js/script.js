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