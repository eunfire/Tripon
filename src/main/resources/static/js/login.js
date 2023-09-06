function start(){
    const allinput = document.querySelectorAll('.body input');
   
   for(let i=0; i<allinput.length-1; i++){
    allinput[i].addEventListener("click", click);
   }
   function click(){
    this.value = null;
    this.style.color="#000000";
   }
        
  

    
    
}
window.addEventListener('load',start);