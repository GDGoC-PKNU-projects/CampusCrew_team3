# CampusCrew AI 서버. BE-A docker-compose 에서 서비스명 ai 로 편입.
FROM python:3.12-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY app ./app

# 내부 전용 포트. 외부 비노출, Docker 네트워크 안에서만 접근.
EXPOSE 8000

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
