# Spring Boot Alerting Service

Демо приложение для отправки алертов в Telegram с использованием Spring Boot и Prometheus Alertmanager.

## Полезные ссылки
- [setup telegram for alerts](https://www.stranatesta.eu/tech/how-to-configure-prometheus-alertmanager-to-send-alerts-to-telegram/)

## Инфраструктура
Для работы нужен alertmanager. Он запускается через docker: [docker-compose.yml](./docker/docker-compose.yml).  
Также понадобиться создать Telegram бота и создать канал, куда будут отправлять алерты.  
Сделать это можно по [инструкции](https://www.stranatesta.eu/tech/how-to-configure-prometheus-alertmanager-to-send-alerts-to-telegram/) из полезных ссылок.

## Логика работы демки

Отправка алертов осуществляется напрямую в Alertmanager через его [rest api](https://github.com/prometheus/alertmanager/blob/main/api/v2/openapi.yaml), минуя Prometheus.

В правилах alertmanager-а настраивается receiver 'telegram' для отправки алертов в Telegram.  

Для тестирования отправки алертов в приложении объявлен endpoint `POST /api/v1/alerts/send`, который принимает JSON c полями:
- name
- reason
- soure

Поля заданы просто так, без особого смысла.  
На основе этих полей затем формируется объект `Alert` и отправляется в Alertmanager.  

Пример запроса:
```http request
### send alert
POST http://localhost:8080/api/v1/alerts/send
Content-Type: application/json

{
  "name": "test_alert",
  "reason": "generated for a demo",
  "source": "rest api"
}
```

Дальше `Alertmanager` отправляет алерт в Telegram, и вы можете его увидеть в своем канале.