echo "url,forks,stargazers" > top100.csv

curl -G https://api.github.com/search/repositories \
   --data-urlencode "sort=stars" \
   --data-urlencode "order=desc" \
   --data-urlencode "q=language:java" |\
   jq -r '.items[] | "\(.clone_url),\(.forks),\(.stargazers_count)"' >>\
   top100.csv
