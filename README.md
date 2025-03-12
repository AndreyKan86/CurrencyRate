# Курс валют
Проект собирающий информацию с сайта val.ru о курсе выбранной валюты за выбранный период, строящий график этого курса, а так же сглаживающий его при помощи нелинейного 
фильтра Калмана и делающий прогноз относительно сглаженного курса валюты на завтрешний день.
## Краткое описание
При запуске придлжения отображется пустой экран, с возможностью выбрать из выпадающего списка валюту и период который нужно отобразить.    
<table>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/dd649d3d-6fcb-408f-8297-80eacc5e4700" alt="Тёмная тема" width="300"/>
      <br>
      <p align="center"> Тёмная тема </p>
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/86d78790-25ba-43dd-803c-8a7a5c6c69af" alt="Светлая тема" width="300"/>
      <br>
      <p align="center"> Светлая тема </p>
    </td>
  </tr>
</table>  
Есть возможность выбора валюты и временного периода.    
<table>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/1bd35943-a501-421d-85dd-282e7d296409" alt="Выбор валюты" width="300"/>
      <br>
      <p align="center"> Выбор валюты </p>
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/865e9f05-895d-4bdb-af74-429924f5d121" alt="Выбор периода" width="300"/>
      <br>
      <p align="center"> Выбор временного периода </p>
    </td>
  </tr>
</table>  
При нажатии кнопки "приступить" появляется текстовый список курсов выбранной валюты за выбранный период, график с реальным курсом выбранной валюты и сглаженным, через нелинейный
фильтр Калмана, значением и прогнозом на следующий день.  
<table>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/019a21db-b8bd-4116-84b5-a288749afa52" alt="Выбор валюты" width="300"/>
      <br>
      <p align="center"> Запущеное приложение </p>
    </td>
  </tr>
</table>  
Есть боковое меню с переключателем цветовых тем и информацией.
<table>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/a5f9dbaf-b5ec-4263-84db-4f75588f2a82" alt="Боковое меню" width="300"/>
      <br>
      <p align="center"> Боковое меню </p>
    </td>
  </tr>
</table>  

## Технологический стек  
* Kotlin
* Jetpack Compose
* ViewModel
* Retrofit
* Material Design
* Coroutine
* Flow
* MPAndroidChart
