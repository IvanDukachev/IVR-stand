const flag = localStorage.getItem('flag');
if (flag == "True") {
  document.getElementById('b1').innerHTML = '<img src="images/pop.gif" alt="gif" id="my-image"><img src="images/1usluga.png" draggable="false" alt="Картинка"><span class="no-copy">Подача заявления на назначение пенсии</span>';
  document.getElementById('b2').innerHTML = '<img src="images/pop.gif" alt="gif"id="my-image"><img src="images/lgota.png" alt="Картинка"><span class="no-copy">Изменение способа доставки пенсии и иных социальных выплат</span>'
  document.getElementById('b3').innerHTML = '<img src="images/pop.gif" alt="gif"id="my-image"><img src="images/prochee.png" alt="Картинка"><span class="no-copy">Получение справки о размере пенсии и иных социальных выплат</span>'
  document.getElementById('b4').innerHTML = '<img src="images/pop.gif" alt="gif"id="my-image"><img src="images/prochee.png" alt="Картинка"><span class="no-copy">Получение свидетельства пенсионера</span>'
  document.getElementById('b5').innerHTML = '<img src="images/pop.gif" alt="gif"id="my-image"><img src="images/prochee.png" alt="Картинка"><span class="no-copy">Назначение компенсационной выплаты по уходу за инвалидом первой группы</span>'
  } else {
    document.getElementById('b1').innerHTML = '<img src="images/1usluga.png" draggable="false" alt="Картинка"><span class="no-copy">Подача заявления на назначение пенсии</span>';
    document.getElementById('b2').innerHTML = '<img src="images/lgota.png" alt="Картинка"><span class="no-copy">Изменение способа доставки пенсии и иных социальных выплат</span>'
    document.getElementById('b3').innerHTML = '<img src="images/prochee.png" alt="Картинка"><span class="no-copy">Получение справки о размере пенсии и иных социальных выплат</span>'
    document.getElementById('b4').innerHTML = '<img src="images/prochee.png" alt="Картинка"><span class="no-copy">Получение свидетельства пенсионера</span>'
    document.getElementById('b5').innerHTML = '<img src="images/prochee.png" alt="Картинка"><span class="no-copy">Назначение компенсационной выплаты по уходу за инвалидом первой группы</span>'
  }