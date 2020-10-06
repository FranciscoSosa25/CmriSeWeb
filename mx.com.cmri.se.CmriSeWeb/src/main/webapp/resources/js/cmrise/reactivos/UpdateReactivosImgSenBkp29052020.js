/**
 * 
 */



 var tipoPregunta = document.getElementById('UpdateReactivosForm:tipoPregunta'); 
 const fileInput = document.getElementById('UpdateReactivosForm:fileImagenSen'); 
 const fileImagenSenDiv = document.getElementById('fileImagenSenDiv');
 var graphicImageImgSenID = document.getElementById('graphicImageImgSenID'); 
 var imgWidth = document.getElementById('UpdateReactivosForm:imgWidth'); 
 var imgHeight = document.getElementById('UpdateReactivosForm:imgHeight'); 
 var canvas = document.getElementById("paintingCanvas"); 
 ctx = canvas.getContext("2d");
 var canvasWidth; 
 var canvasHeight; 
 
 console.log(tipoPregunta); 
 console.log(imgWidth); 
 console.log(imgHeight); 
 
 graphicImageImgSenID.addEventListener('load',(e) =>{
	    console.log('Comienza onload UpdateReactivosForm:graphicImageImgSenID'); 
	    console.log(e); 
	    console.log('Finaliza onload UpdateReactivosForm:graphicImageImgSenID'); 
     }); 
 
 graphicImageImgSenID.addEventListener('change',(e) =>{
	    console.log('Comienza onchange UpdateReactivosForm:graphicImageImgSenID'); 
	    console.log(e); 
	    console.log('Finaliza onchange UpdateReactivosForm:graphicImageImgSenID'); 
  });
 
 fileInput.addEventListener('change', (e) => {
	    console.log("Hola1"); 
	    const file = e.target.files[0]; 
	    const fileReader = new FileReader(); 
	    fileReader.readAsDataURL(file); 
	    fileReader.addEventListener('load',(e)=>{
	      	 var img = new Image();
	    	 img.setAttribute('src', e.target.result); 
	    	 img.addEventListener('load',(e)=>{
	    	    console.log(img.width); 
	    	    console.log(img.height); 
	    	    canvasWidth = img.width;
	    	    canvasHeight = img.height; 
	    		fileImagenSenDiv.style.width = img.width+"px";
		    	fileImagenSenDiv.style.height = img.height+"px";
		    	canvas.style.width = img.width+"px";
		    	canvas.style.height = img.height+"px";
	    	 }); 
	    	
	    	fileImagenSenDiv.style.backgroundImage = 'url("'+e.target.result+'")'; 
		   
	    }); 
	      
	}); 
 
 function oncompleteFileUpload(){
	 var graphicImageImgSenID = document.getElementById('graphicImageImgSenID'); 
	 var canvas = document.getElementById("paintingCanvas"); 
	 console.log("canvasWidth:"+canvasWidth);
	 console.log("canvasHeight:"+canvasHeight);
	 canvas.width = canvasWidth; 
	 canvas.height = canvasHeight; 
	 ctx = canvas.getContext("2d");
	 ctx.drawImage(graphicImageImgSenID,0,0,canvas.width,canvas.height);  
	 
	 var fileInput = document.getElementById('UpdateReactivosForm:fileImagenSen'); 
	 
	 fileInput.addEventListener('change', (e) => {
		    console.log("Hola1"); 
		    const file = e.target.files[0]; 
		    const fileReader = new FileReader(); 
		    fileReader.readAsDataURL(file); 
		    fileReader.addEventListener('load',(e)=>{
		      	 var img = new Image();
		    	 img.setAttribute('src', e.target.result); 
		    	 img.addEventListener('load',(e)=>{
		    	    console.log(img.width); 
		    	    console.log(img.height); 
		    	    canvasWidth = img.width;
		    	    canvasHeight = img.height; 
		    		fileImagenSenDiv.style.width = img.width+"px";
			    	fileImagenSenDiv.style.height = img.height+"px";
			    	canvas.style.width = img.width+"px";
			    	canvas.style.height = img.height+"px";
		    	 }); 
		    	
		    	fileImagenSenDiv.style.backgroundImage = 'url("'+e.target.result+'")'; 
			   
		    }); 
		});
	 
	 canvas.addEventListener("mousemove", function(e) {
		 var cRect = canvas.getBoundingClientRect(); // Gets CSS pos, and width/height
		 var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
		 var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make
		 document.getElementById('UpdateReactivosForm:coordinatesMouseMove').value = "X: "+canvasX+", Y: "+canvasY;
	 });
	 
 }
 
 
 var perimeter = new Array();
 var complete = false;
 var canvas = document.getElementById("paintingCanvas");
 var ctx = canvas.getContext("2d");

 
 canvas.addEventListener("mousemove", function(e) {
	 var cRect = canvas.getBoundingClientRect(); // Gets CSS pos, and width/height
	 var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
	 var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make
	 ctx.clearRect(0, 0, canvas.width, canvas.height); // (0,0) the top left of the canvas
	 ctx.fillText("X: "+canvasX+", Y: "+canvasY, 10, 20);
	 document.getElementById('UpdateReactivosForm:coordinatesMouseMove').value = "X: "+canvasX+", Y: "+canvasY;
 });
 
 function line_intersects(p0, p1, p2, p3) {
     var s1_x, s1_y, s2_x, s2_y;
     s1_x = p1['x'] - p0['x'];
     s1_y = p1['y'] - p0['y'];
     s2_x = p3['x'] - p2['x'];
     s2_y = p3['y'] - p2['y'];

     var s, t;
     s = (-s1_y * (p0['x'] - p2['x']) + s1_x * (p0['y'] - p2['y'])) / (-s2_x * s1_y + s1_x * s2_y);
     t = ( s2_x * (p0['y'] - p2['y']) - s2_y * (p0['x'] - p2['x'])) / (-s2_x * s1_y + s1_x * s2_y);

     if (s >= 0 && s <= 1 && t >= 0 && t <= 1)
     {
         // Collision detected
         return true;
     }
     return false; // No collision
 }

 function point(x, y){
     ctx.fillStyle="white";
     ctx.strokeStyle = "white";
     ctx.fillRect(x-2,y-2,4,4);
     ctx.moveTo(x,y);
 }

 function undo(){
     ctx = undefined;
     perimeter.pop();
     complete = false;
     start(true);
 }

 function clear_canvas(){
     ctx = undefined;
     perimeter = new Array();
     complete = false;
     document.getElementById('UpdateReactivosForm:coordinates').value = '';
     start();
 }

 function draw(end){
     ctx.lineWidth = 1;
     ctx.strokeStyle = "white";
     ctx.lineCap = "square";
     ctx.beginPath();

     for(var i=0; i<perimeter.length; i++){
         if(i==0){
             ctx.moveTo(perimeter[i]['x'],perimeter[i]['y']);
             end || point(perimeter[i]['x'],perimeter[i]['y']);
         } else {
             ctx.lineTo(perimeter[i]['x'],perimeter[i]['y']);
             end || point(perimeter[i]['x'],perimeter[i]['y']);
         }
     }
     if(end){
         ctx.lineTo(perimeter[0]['x'],perimeter[0]['y']);
         ctx.closePath();
         ctx.fillStyle = 'rgba(255, 0, 0, 0.5)';
         ctx.fill();
         ctx.strokeStyle = 'blue';
         complete = true;
     }
     ctx.stroke();

     // print coordinates
     if(perimeter.length == 0){
         document.getElementById('UpdateReactivosForm:coordinates').value = '';
     } else {
         document.getElementById('UpdateReactivosForm:coordinates').value = JSON.stringify(perimeter);
     }
 }

 function check_intersect(x,y){
     if(perimeter.length < 4){
         return false;
     }
     var p0 = new Array();
     var p1 = new Array();
     var p2 = new Array();
     var p3 = new Array();

     p2['x'] = perimeter[perimeter.length-1]['x'];
     p2['y'] = perimeter[perimeter.length-1]['y'];
     p3['x'] = x;
     p3['y'] = y;

     for(var i=0; i<perimeter.length-1; i++){
         p0['x'] = perimeter[i]['x'];
         p0['y'] = perimeter[i]['y'];
         p1['x'] = perimeter[i+1]['x'];
         p1['y'] = perimeter[i+1]['y'];
         if(p1['x'] == p2['x'] && p1['y'] == p2['y']){ continue; }
         if(p0['x'] == p3['x'] && p0['y'] == p3['y']){ continue; }
         if(line_intersects(p0,p1,p2,p3)==true){
             return true;
         }
     }
     return false;
 }

 function point_it(event) {
	  canvas = document.getElementById("paintingCanvas");
	  console.log(event); 
     if(complete){
         alert('Polygon already created');
         return false;
     }
     var rect, x, y;

      console.log(event.ctrlKey); 
      console.log(event.which);
      console.log(event.button);
       
     if(event.ctrlKey || event.which === 3 || event.button === 2){
         if(perimeter.length==2){
             alert('You need at least three points for a polygon');
             return false;
         }
         x = perimeter[0]['x'];
         y = perimeter[0]['y'];
         
         if(check_intersect(x,y)){
             alert('The line you are drowing intersect another line');
             return false;
         }
         draw(true);
         alert('Polygon closed');
 	event.preventDefault();
         return false;
     } else {
         rect = canvas.getBoundingClientRect();
         console.log(canvas); 
         console.log(rect); 
         console.log('event.clientX:'+event.clientX); 
         console.log('event.clientY:'+event.clientY);
         console.log('rect.left:'+rect.left);
         console.log('rect.top:'+rect.top);
         
         x = event.clientX - rect.left;
         y = event.clientY - rect.top;
         console.log('x:'+x); 
         console.log('y:'+y); 
         x =  x.toFixed(5);
         y = y.toFixed(5);
         console.log('x:'+x); 
         console.log('y:'+y); 
         
         if (perimeter.length>0 && x == perimeter[perimeter.length-1]['x'] && y == perimeter[perimeter.length-1]['y']){
             // same point - double click
             return false;
         }
         if(check_intersect(x,y)){
             alert('The line you are drowing intersect another line');
             return false;
         }
         perimeter.push({'x':x,'y':y});
         draw(false);
         return false;
     }
 }

 function start(with_draw) {
     var img = new Image();
     img.src = canvas.getAttribute('data-imgsrc');

     img.onload = function(){
         ctx = canvas.getContext("2d");
         ctx.drawImage(img, 0, 0, canvas.width, canvas.height);
         if(with_draw == true){
             draw(false);
         }
     }
 }
 