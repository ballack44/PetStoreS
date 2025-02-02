BUGS/PROBLEMS found:
1) POST /pet -> the server easily accepts Pet body with missing mandatory values. It shall not accept invalid pet data (missing values, unsupported status type etc.)
2) POST /pet/{petID} -> the server refuses to accept a request (status code 415)!! The response is also weird, because it contains "unknown" type tags, which is not very informative.
3) The task was to check if reponse time is within 500ms, which never happened. So I have made some offset.
