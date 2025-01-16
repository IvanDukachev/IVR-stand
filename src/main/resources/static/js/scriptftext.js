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

    var element = document.querySelector('.container1');
    var elementId = element.id;
    fetch('/descriptions/' + 'info' + elementId + '.txt')
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
const feedback = document.getElementById("myFeedback");
const openFeedbackButton = document.getElementById("openFeedback");

openFeedbackButton.addEventListener("click", function () {
    feedback.style.display = "flex";
  });
const closeFeedBackButton = document.querySelector(".close1");

closeFeedBackButton.addEventListener("click", function () {
    feedback.style.display = "none";
});