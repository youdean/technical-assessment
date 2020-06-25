# README #

Demo Program for Technical Assessment

### Prerequisites
docker and docker-compose

## How to run?
 - docker-compose up
 
## How to use?
1. Buka browser: http://localhost/swagger-ui.html
2. Buat user baru di endpoint /users atau gunakan user default: user@domain.com / Password123!
3. Generate token di /auth/token
4. Klik tombol Authorize masukkan token hasil generate: "Bearer {token}" tanpa tanda petik
5. token digunakan untuk mengakses API lain