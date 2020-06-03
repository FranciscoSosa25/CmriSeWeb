/**
 * 
 */

var contadorClicks = 0;
var puntoCreado = false;
var tipoReactivoID; 
var paintingCanvasID; 
var graphicImageImgSenID; 
var widthImgSenID; 
var heightImgSenID; 
var ctx; 
var perimeter = new Array();

document.addEventListener('DOMContentLoaded', () => {
  console.log('Comienza DOMContentLoaded');
  tipoReactivoID = document.getElementById('previewForm:tipoReactivo'); 
  paintingCanvasID = document.getElementById('paintingCanvas');
  graphicImageImgSenID = document.getElementById('previewForm:graphicImageImgSen');
  widthImgSenID = document.getElementById('previewForm:widthImgSen');
  heightImgSenID = document.getElementById('previewForm:heightImgSen');
  
  console.log(tipoReactivoID.value); 
  if(tipoReactivoID.value!=='IMAGEN_INDICADA'){
	  return; 
  }
  
  var img = new Image();
  img.setAttribute('src', graphicImageImgSenID.src); 
  img.addEventListener('load',(e)=>{
	  console.log(img.width); 
	  console.log(img.height); 
	  paintingCanvasID.width = img.width; 
	  paintingCanvasID.height = img.height; 
	  ctx = paintingCanvasID.getContext("2d");
	  ctx.drawImage(img, 0, 0, paintingCanvasID.width, paintingCanvasID.height);
	     
  }); 
  
  
  paintingCanvasID.addEventListener("mousemove", function(e) {
 	 var cRect = paintingCanvasID.getBoundingClientRect(); // Gets CSS pos, and width/height
 	 var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
 	 var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make
 	 document.getElementById('previewForm:coordinatesMouseMove').value = "X: "+canvasX+", Y: "+canvasY;
  });
  
  console.log('Finaliza DOMContentLoaded'); 
}); 


function point_it(event) {
  perimeter = new Array();	
  paintingCanvasID = document.getElementById("paintingCanvas");
  console.log(event); 
  var rect, x, y;

   console.log(event.ctrlKey); 
   console.log(event.which);
   console.log(event.button);
    
  if(event.ctrlKey || event.which === 3 || event.button === 2){
      event.preventDefault();
      return; 
  }
  
  rect = paintingCanvasID.getBoundingClientRect();
  x = event.clientX - rect.left;
  y = event.clientY - rect.top;
  x =  x.toFixed(5);
  y = y.toFixed(5);
  perimeter.push({'x':x,'y':y});
  
 
  var tmpX,tmpY; 
  
  ctx.strokeStyle='white';
  ctx.beginPath();
  ctx.moveTo(x,y);
  tmpX = x+20; 
  ctx.lineTo(tmpX,y);
  tmpX = x-20; 
  ctx.lineTo(tmpX,y);
  ctx.moveTo(x,y);
  tmpY = y+20; 
  ctx.lineTo(x,tmpY);
  tmpY = y-20; 
  ctx.lineTo(x,tmpY);
  ctx.lineWidth=2;
  ctx.stroke();


  /*
  ctx.lineWidth = 5;
  ctx.strokeStyle = "white";
  ctx.lineCap = "square";
  ctx.beginPath();
  ctx.moveTo(perimeter[0]['x'],perimeter[0]['y']);
  ctx.lineTo(perimeter[0]['x'],perimeter[0]['y']);
  ctx.moveTo(perimeter[0]['x']+5,perimeter[0]['y']);
  ctx.lineTo(perimeter[0]['x']+5,perimeter[0]['y']);
  ctx.moveTo(perimeter[0]['x']+10,perimeter[0]['y']);
  ctx.lineTo(perimeter[0]['x']+10,perimeter[0]['y']);
  ctx.closePath();
  ctx.stroke();
  */
  
  
}

function init(){
			var divArea = document.getElementById('myDiagramDiv');
			divArea.onclick = function() {
			    addShape();
			};

		}
		
		function addShape(){	
			contadorClicks = contadorClicks + 1;
			var numClicks = contadorClicks;
			
			if(!puntoCreado){
				puntoCreado = true				
		  		var $ = go.GraphObject.make;
				myDiagram = $(go.Diagram, "myDiagramDiv");
				myDiagram.add(
				    $(go.Part, "auto",
	    				$(go.Shape, "PlusLine", { width: 40, height: 40, margin: 4, fill: "red", stroke: "red", strokeWidth: 2 })
				    )
				);			
			}else{
				//alert('logica faltante');
			}
		}
		

		$(document).ready(function() {
			 console.log('Hola');	 
			init(); 
			  });		