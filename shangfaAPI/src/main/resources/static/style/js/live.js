axios.default.withCredentials = true
let prefix = "/"
function keepalive() {
    axios.get(prefix+"checkalive").then((res,rej)=>{
        //console.log(res)
    })
}
let timer = setInterval(keepalive,10*1000)