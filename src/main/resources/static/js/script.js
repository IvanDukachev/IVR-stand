const flag = localStorage.getItem('flag');
console.log(flag)
//if (flag == 1) {
//    document.getElementById('1').innerHTML = '<img src="images/pop.gif" alt="Картинка" id="my-image"><img src="images/1usluga.png" draggable="false" alt="Картинка">';
//    document.getElementById('2').innerHTML = '<img src="images/pop.gif" alt="Картинка" id="my-image"><img src="images/lgota.png" alt="Картинка">'
//    document.getElementById('3').innerHTML = '<img src="images/pop.gif" alt="Картинка" id="my-image"><img src="images/prochee.png" alt="Картинка">'
//  } else {
//    document.getElementById('1').innerHTML = '<img src="images/1usluga.png" draggable="false" alt="Картинка">';
//    document.getElementById('2').innerHTML = '<img src="images/lgota.png" alt="Картинка">'
//    document.getElementById('3').innerHTML = '<img src="images/prochee.png" alt="Картинка">'
//  }

function setButtonId(buttonId) {
  document.getElementById('buttonId').value = buttonId;
}

// Функция для отправки формы только после установки ID
function submitFormWithId() {
const buttonId = document.getElementById('buttonId').value;
if (buttonId) {
  return true; // Отправляем форму
} else {
  alert("Пожалуйста, выберите категорию."); // Сообщение, если ID не выбран
  return false; // Останавливаем отправку
}
}

/*document.getElementById('back-button').addEventListener('click', function() {
    window.history.back();
});*/



document.addEventListener("DOMContentLoaded", function () {
  const modal = document.getElementById("myModal");
  const openModalButton = document.getElementById("openModal");
  const closeModalButton = document.querySelector(".close");
  const backModalButton = document.getElementById("back");

  document.getElementById('rcg').innerHTML = '<span class="no-copy">Начать запись</span>';

  const startRecordingButton = document.getElementById("rcg"); // Кнопка "Начать запись"
  const videoElement = document.getElementById("cameraStream");
  let stream;
  let isRecording = false; // Переменная для отслеживания состояния записи

  // Открытие модального окна
  openModalButton.addEventListener("click", function () {
    modal.style.display = "flex";
  });

  // Закрытие модального окна
  closeModalButton.addEventListener("click", function () {
    modal.style.display = "none";
    document.getElementById('rcg').innerHTML = '<span class="no-copy">Начать запись</span>'; // Меняем текст кнопки
    // Останавливаем камеру, если запись была начата
    stopCamera();
  });

  backModalButton.addEventListener("click", function () {
    modal.style.display = "none";
    document.getElementById('rcg').innerHTML = '<span class="no-copy">Начать запись</span>'; // Меняем текст кнопки
    stopCamera();
  });

  // Запуск или остановка записи при нажатии на кнопку
  startRecordingButton.addEventListener("click", function () {
    if (isRecording) {
      stopCamera(); // Останавливаем камеру
      document.getElementById('rcg').innerHTML = '<span class="no-copy">Начать запись</span>'; // Меняем текст кнопки
    } else {
      if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia({ video: true })
          .then(function (cameraStream) {
            stream = cameraStream;
            videoElement.srcObject = stream;
            document.getElementById('rcg').innerHTML = '<span class="no-copy">Остановить запись</span>'; // Меняем текст кнопки
          })
          .catch(function (error) {
            alert("Ошибка доступа к камере: ", error);
            console.error("Ошибка доступа к камере: ", error);
          });
      }
    }
    isRecording = !isRecording; // Переключаем состояние записи
  });

  // Функция остановки камеры
  function stopCamera() {
    if (stream) {
      const tracks = stream.getTracks();
      tracks.forEach(track => track.stop());
      videoElement.srcObject = null;
    }
  }

  // Закрытие модального окна при клике вне его
  window.addEventListener("click", function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
      stopCamera();
      document.getElementById('rcg').innerHTML = '<span class="no-copy">Начать запись</span>'; // Меняем текст кнопки
    }
  });
});

function onclick1() {
  document.getElementById('userInput1').value = "заявление";
}