# Learning Spring Boot 3.0

This repo is the application of this Book [Learning Spring Boot 3.0](https://www.amazon.com/-/es/Greg-L-Turnquist/dp/1803233303/ref=sr_1_1?keywords=learning+spring+boot+3.0&qid=1686258700&sprefix=learning+spring+%2Caps%2C224&sr=8-1])


## Services:


curl localhost:9000/api/videos

curl -v -X POST localhost:9000/api/videos -d '{"name": "Learning Spring Boot 3"}' -H 'Content-type:application/json'

curl -v -X POST localhost:9000/api/videos/multi-field-search -d '{"name":"e"}' -H 'Content-type:application/json'