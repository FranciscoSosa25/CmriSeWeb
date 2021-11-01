

       var scale = 1.0;
      var scaleMultiplier = 0.8;

var poligonos=0;
var zoomIntensity = 0.1;
var cursorPos;
var canMouseX, canMouseY;
var playSeries = {
		last: 0,
		intervalMs : 75,
		scale : 1,
		contrast : 0,
		zoomIntensity : 0.8,
		isDragging : false,
		canvas : "",
		dicomJSON : {},
		img : new Image(),
		activeButton : 1,
		getTranslatePos : function() {
		 return	{
		        x: playSeries.canvas.width / 2,
		        y: playSeries.canvas.height / 2
		      };
		}, 
		showPause: function(){
			$('#'+playSeries.playBtn.replaceAll(":","\\:")+' > span.ui-button-icon-left').removeClass("fa-play fa-pause").addClass("fa-pause");
		},
		showPlay: function(){
			$('#'+playSeries.playBtn.replaceAll(":","\\:")+' > span.ui-button-icon-left').removeClass("fa-play fa-pause").addClass("fa-play");
		},
		setActiveBtn : function(val){
			playSeries.resetImage();
			playSeries.activeButton = val
		},
		contrastImage : function(imageData, contrast){
			  var data = imageData.data;  // Note: original dataset modified directly!
			    contrast *= 255;
			    var factor = (contrast + 255) / (255.01 - contrast);  //add .1 to avoid /0 error.

			    for(var i=0;i<data.length;i+=4)
			    {
			        data[i] = factor * (data[i] - 128) + 128;
			        data[i+1] = factor * (data[i+1] - 128) + 128;
			        data[i+2] = factor * (data[i+2] - 128) + 128;
			    }
			    return imageData;  //optional (e.g. for filter function ch
			
		},
		bindMouseWheel : function(divSeries){
			$('#'+divSeries).bind('mousewheel', function(e){
				  var now = new Date().getTime();
				  if (playSeries.last + playSeries.intervalMs < now && playSeries.dicomJSON.series ) {
					  playSeries.last = now;
					  if(playSeries.timeOut){
							clearInterval(playSeries.timeOut)
							playSeries.timeOut = undefined
						}
					  
					  if(e.originalEvent.wheelDelta /120 > 0) {
						  switch (playSeries.activeButton) {
							case 2:
								playSeries.zoomIn()
								break;
							case 3:
								playSeries.contrastIn()
								break;
							default:
								playSeries.playNext()
						  }
				        }
				        else{
							  switch (playSeries.activeButton) {
								case 2:
									playSeries.zoomOut()
									break;
								case 3:
									playSeries.contrastOut()
									break;
								default:
									playSeries.playPrev()
							  }
				        }
					  playSeries.showPlay();
				  }
		        e.preventDefault();
		    });
			
			
			$('#'+divSeries).bind('mousedown', function(e){
				if(playSeries.activeButton==2){
					var canvasOffset=$('#'+divSeries).offset();
				    var offsetX=canvasOffset.left;
				    var offsetY=canvasOffset.top;
				    
					canMouseX=parseInt(e.clientX-offsetX);
				    canMouseY=parseInt(e.clientY-offsetY);
				    playSeries.isDragging=true;
				}
		    });
			
			$('#'+divSeries).bind('mousemove', function(e){
				if(playSeries.activeButton==2){
					var canvasOffset=$('#'+divSeries).offset();
				    var offsetX=canvasOffset.left;
				    var offsetY=canvasOffset.top;
				    
					canMouseX=parseInt(e.clientX-offsetX);
				      canMouseY=parseInt(e.clientY-offsetY);
				      // if the drag flag is set, clear the canvas and draw the image
				      if(playSeries.isDragging){
				    	  	playSeries.canvas.clearRect(0, 0, 510, 510);
							playSeries.canvas.save();
							playSeries.canvas.scale(playSeries.scale, playSeries.scale)
							playSeries.canvas.drawImage(playSeries.img,canMouseX-510/2,canMouseY-510/2,510,510);
							playSeries.canvas.restore();
				      }
			      
				} 
		    });

			
			$('#'+divSeries).bind('mouseup', function(e){
				if(playSeries.activeButton==2){
					var canvasOffset=$('#'+divSeries).offset();
				    var offsetX=canvasOffset.left;
				    var offsetY=canvasOffset.top;

					canMouseX=parseInt(e.clientX-offsetX);
				    canMouseY=parseInt(e.clientY-offsetY);
				    playSeries.isDragging=false;
				}
		    });

			
			$('#'+divSeries).bind('mouseout', function(e){
				if(playSeries.activeButton==2){
					var canvasOffset=$('#'+divSeries).offset();
				    var offsetX=canvasOffset.left;
				    var offsetY=canvasOffset.top;
					canMouseX=parseInt(e.clientX-offsetX);
				    canMouseY=parseInt(e.clientY-offsetY);
				}
		    });

			
			
			
			
			
			
			
			
		},
		getCanvasImg : function(){
			if(playSeries.img){
				return img
			}else{
				playSeries.img = new Image();
			}
		},
		addPoint : function(event){
			if(!playSeries.dicomJSON.series){
				alert("Seleccione la serie DICOM para dibujar puntos.")
				return;
			}
			if(playSeries.activeButton != 1){
				return;
			}
			var dicom = playSeries.dicomJSON.series[playSeries.index];
			
			if(!dicom.points){
				dicom.points = [];
			}
			
			let pVal = dicom.points.length;
			let noOfpolygon = dicom.numeroPoligonos;
			if(pVal >=  noOfpolygon){
				return;
			}
    	   var rect =playSeries.canvasDiv.getBoundingClientRect(); 	
		   x = event.clientX - rect.left;
	       y = event.clientY - rect.top;
	       x = x.toFixed(5);
	       y = y.toFixed(5);
	       dicom.points.push({'x': x, 'y': y, color: 'blue'});	
	       playSeries.drawCursor(x,y, 'blue')
		},
		drawCursor : function(x,y, color){
			var tmpX, tmpY;
			var ctx = playSeries.canvas;
			var strColor;
			switch(color){
			case 'red':
				strColor = '#ff0000'
				break;
			case 'green':
				strColor = '#00ff00'
				break;	
			default:
				strColor = '#0000ff'
			}
			
	        ctx.strokeStyle = strColor;
	        ctx.beginPath();
	        tmpX = parseFloat(x) + 20;
	        ctx.moveTo(tmpX, y);
	        ctx.lineTo(x, y);
	        tmpX = parseFloat(x) - 20;
	        ctx.moveTo(tmpX, y);
	        ctx.lineTo(x, y);
	        tmpY = parseFloat(y) + 20;
	        ctx.moveTo(x, tmpY);
	        ctx.lineTo(x, y);
	        tmpY = parseFloat(y) - 20;
	        ctx.moveTo(x, tmpY);
	        ctx.lineTo(x, y);
	        ctx.lineWidth = 3;
	        ctx.stroke();
		},
		drawPoints : function(){
			var dicom = playSeries.dicomJSON.series[playSeries.index];
			if(dicom.points){
				for(var i=0;i<dicom.points.length;i++){
					var points = dicom.points[i];
					playSeries.drawCursor(points.x, points.y, points.color)
				}
			}
		},
		clearPoint : function(){
		    var dicom = playSeries.dicomJSON.series[playSeries.index];
			if(dicom.points){
				playSeries.canvas.clearRect(0, 0, 510, 510);
			    playSeries.canvas.drawImage(playSeries.img, 0, 0, 510, 510);
			    dicom.points = [];
			    dicom.puntoCorrectos = 0;
			    dicom.score=0;
			}
		},
		saveDrawPoints : function(){
			document.getElementById(playSeries.respuestasId).value = JSON.stringify(playSeries.dicomJSON);
		},
		updateSeries : function(series){
			playSeries.dicomJSON = JSON.parse(document.getElementById(series).value);
			playSeries.changeImage()
		},playNext : function(){
			playSeries.index++;
			playSeries.changeImage();
		},
		contrastIn : function(){
			playSeries.contrast += 0.05
			if(playSeries.contrast >= 0.50 ){
				playSeries.resetImage()
				return;
			}
			playSeries.canvas.save();
		    var origBits = playSeries.canvas.getImageData(0, 0, 510, 510);
		    playSeries.contrastImage(origBits, (playSeries.contrast).toFixed(2));	
			playSeries.canvas.putImageData(origBits, 0, 0)
			playSeries.canvas.restore();
		},
		contrastOut : function(){
			playSeries.contrast -= 0.05
			if(playSeries.contrast <= -0.50) {
				playSeries.resetImage()
				return;
			}
			playSeries.canvas.save();
			var origBits = playSeries.canvas.getImageData(0, 0, 510, 510);
		    playSeries.contrastImage(origBits, -(playSeries.contrast).toFixed(2));	
			playSeries.canvas.putImageData(origBits, 0, 0)
			playSeries.canvas.restore();
		},
		zoomIn : function(){
			playSeries.scale += 0.1;
			playSeries.canvas.clearRect(0, 0, 510, 510);
			playSeries.canvas.save();
			playSeries.canvas.scale(playSeries.scale, playSeries.scale)
		    playSeries.canvas.drawImage(playSeries.img, 0, 0, 510, 510);
			playSeries.canvas.restore();
		},
		zoomOut : function(){
			playSeries.scale -= 0.1;
			playSeries.canvas.clearRect(0, 0, 510, 510);
			playSeries.canvas.save();
			playSeries.canvas.scale(playSeries.scale, playSeries.scale)
		    playSeries.canvas.drawImage(playSeries.img, 0, 0, 510, 510);
			playSeries.canvas.restore();
		},
		playPrev : function(){
			playSeries.index--;
			playSeries.changeImage();
		},
		playNow: function(){
			try{
			var len = playSeries.dicomJSON.series.length
			if(playSeries.index == undefined ||  playSeries.index >= len){
				playSeries.index = 0;
			} 
			playSeries.changeImage();
			playSeries.index++;
			
			if(playSeries.index >= len){
				playSeries.index = 0;
			}
			}catch (e) {
				playSeries.showPlay()
				clearInterval(playSeries.timeOut)	
			}
		},
		play: function(reset){
			playSeries.activeButton = 1;
			if(playSeries.timeOut){
				clearInterval(playSeries.timeOut)
				playSeries.timeOut = undefined
				playSeries.showPlay();
				if(!reset){
					return;
				}
			}
			playSeries.timeOut = setInterval(function(){ playSeries.playNow() }, 200);
			playSeries.showPause()
			
		},
		resetPlayer : function(){
			playSeries.activeButton = 1;
			playSeries.resetImage();
			
		},
		resetImage: function(){
			playSeries.contrast = 0;
			playSeries.scale = 1;
			playSeries.canvas.drawImage(playSeries.img, 0, 0, 510, 510);
         	playSeries.drawPoints();
			
		},
		changeImage: function(){
			var len = playSeries.dicomJSON.series.length
			if(playSeries.index >= len){
				playSeries.index = len-1
			}
			if(playSeries.index <= 0){
				playSeries.index = 0;
			}
			var dicom = playSeries.dicomJSON.series[playSeries.index];
			document.getElementById(playSeries.divSeries+'Index').textContent = playSeries.index+1 +'/'+len;
			document.getElementById(playSeries.divSeries+'Poly').textContent = dicom.numeroPoligonos
			document.getElementById(playSeries.divSeries+'PuntoCorrect').textContent = dicom.score
			playSeries.img.setAttribute('src', 'data:jpeg;base64,'+dicom.jpgBase64);
         	playSeries.canvas.drawImage(playSeries.img, 0, 0, 510, 510);
         	playSeries.drawPoints();
			
		},
		stop: function(){
			if(playSeries.timeOut){
				clearInterval(playSeries.timeOut)
				playSeries.timeOut = undefined
				playSeries.showPlay();
			}
		}
		
};
var i = 0;

var polyModel = {
		setCanvasDimention : function(canvas, height, width){
			 let canvasContainer = document.getElementById(canvas);
			 let h = document.getElementById(height).value;
			 let w = document.getElementById(width).value;
			 canvasContainer.style.width = h + 'px';
			 canvasContainer.style.height = w + 'px';
		},
		drawCanvas : function(canvas, canvasContainer, height, width, cursor, ansPoints){
			var imgSrc = jQuery('#'+canvasContainer).find('img').attr('src')
			if(imgSrc == undefined || imgSrc === 'data:jpeg;base64,'){
				return false
			}
			document.getElementById(canvas).style.display = "block";
			
			var img = new Image();
	        img.setAttribute('src', imgSrc);
	        paintingCanvasID = document.getElementById(canvas);
         	paintingCanvasID.width = document.getElementById(width).value;
         	paintingCanvasID.height = document.getElementById(height).value;
         	
		    ctx = paintingCanvasID.getContext("2d");
		    //ctx.clearRect(0, 0, paintingCanvasID.width, paintingCanvasID.height);
		    ctx.drawImage(img, 0, 0, paintingCanvasID.width, paintingCanvasID.height);
         	
         	
         	if(ansPoints){
				try{
				var val = document.getElementById(ansPoints)
				if(val && val.value != "[]"  && val.value != ""){
					polyModel.addAllPoints(canvas, val.value)
				}else{
					val.value = "[]";
				}
								}catch (e) {
				}
			}else{
				
			}
		    
		    if(cursor){
		    	cursorPos = cursor;
			    paintingCanvasID.addEventListener("mousemove", function (e, cursor) {
		            var cRect = paintingCanvasID.getBoundingClientRect(); // Gets CSS pos, and width/height
		            var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
		            var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make
		            document.getElementById(cursorPos).value = "X: " + canvasX + ", Y: " + canvasY;
		        });
		    }
		},
		addAllPoints: function(canvas, pVal){
			if(pVal){
				points = JSON.parse(pVal);
				for(var i = 0; i < points.length; i++) {
				    var obj = points[i];
				    var x = obj.x;
				    var y = obj.y;
				    var tmpX, tmpY;
					var ctx = canvas;
					var strColor;
			        ctx.strokeStyle = '#ff0000';
			        ctx.beginPath();
			        tmpX = parseFloat(x) + 20;
			        ctx.moveTo(tmpX, y);
			        ctx.lineTo(x, y);
			        tmpX = parseFloat(x) - 20;
			        ctx.moveTo(tmpX, y);
			        ctx.lineTo(x, y);
			        tmpY = parseFloat(y) + 20;
			        ctx.moveTo(x, tmpY);
			        ctx.lineTo(x, y);
			        tmpY = parseFloat(y) - 20;
			        ctx.moveTo(x, tmpY);
			        ctx.lineTo(x, y);
			        ctx.lineWidth = 3;
			        ctx.stroke();
				}
				
			}	
		},
		
		drawPoint : function(event, canvas,ansPoints,numeroPolygon){
			var paintingCanvasID = document.getElementById(canvas);
			let pVal = document.getElementById(ansPoints).value;
			let noOfpolygon = document.getElementById(numeroPolygon).value;
			
			let clickCount = 0;
			let points = [];
			
			if(pVal){
				points = JSON.parse(pVal);
				if(points.length >=  noOfpolygon){
					return;
				}
			}else{
				points = [];
			}
			
			rect = paintingCanvasID.getBoundingClientRect(); 	
		   ctx = paintingCanvasID.getContext("2d");		
		   x = event.clientX - rect.left;
	       y = event.clientY - rect.top;
	       x = x.toFixed(5);
	       y = y.toFixed(5);
	       points.push({'x': x, 'y': y});	
	       polyModel.drawCursor(ctx, x,y)
	       document.getElementById(ansPoints).value = JSON.stringify(points);
		},
		loadDICOMPlayer : function(divSeries, canvas, playBtn, cursor){
			playSeries.divSeries = divSeries;
			playSeries.bindMouseWheel(divSeries)
			playSeries.playBtn = playBtn;
			playSeries.cursor = cursor;
			playSeries.index = 0;
			cursorPos = playSeries.cursor;
			playSeries.canvasDiv = document.getElementById(playSeries.divSeries+'Canvas');
			playSeries.canvas = document.getElementById(playSeries.divSeries+'Canvas').getContext("2d");
			playSeries.canvasDiv.style.display = "block";
			playSeries.canvasDiv.width = 510;
			playSeries.canvasDiv.height = 510;
			playSeries.canvasDiv.addEventListener("mousemove", function (e, cursor) {
	            var cRect = playSeries.canvasDiv.getBoundingClientRect(); // Gets CSS pos, and width/height
	            var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
	            var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make
	            document.getElementById(cursorPos).textContent = "X: " + canvasX + ", Y: " + canvasY;
	        });
		}, 
		loadSeries: function(imageJSON, respuestasId){
			playSeries.dicomJSONDiv = imageJSON;
			playSeries.dicomJSON = JSON.parse(document.getElementById(imageJSON).value);
			playSeries.respuestasId = respuestasId
			playSeries.play(true);
		},
		
				
		clearPoint : function(canvas, canvasContainer, height, width, cursor, ansPoints, scorePoints){
			let paintingCanvasID = document.getElementById(canvas);
		    ctx = paintingCanvasID.getContext("2d");
		    document.getElementById(ansPoints).value = '[]';
		    ctx.clearRect(0, 0, paintingCanvasID.width, paintingCanvasID.height);		
		    polyModel.drawCanvas(canvas, canvasContainer, height, width, cursor, ansPoints)
		    polyModel.clearScore(scorePoints)
		},
		clearScore: function(scorePoints){
			try{
			let score = document.getElementById(scorePoints);
				if(score){
					score.innerText = '0.0';
				}
			}catch(e){
				
			}
			
		},
		drawCursor : function(ctx, x, y){
			  var tmpX, tmpY;
		        ctx.strokeStyle = 'red';
		        ctx.beginPath();
		        tmpX = parseFloat(x) + 20;
		        ctx.moveTo(tmpX, y);
		        ctx.lineTo(x, y);
		        tmpX = parseFloat(x) - 20;
		        ctx.moveTo(tmpX, y);
		        ctx.lineTo(x, y);
		        tmpY = parseFloat(y) + 20;
		        ctx.moveTo(x, tmpY);
		        ctx.lineTo(x, y);
		        tmpY = parseFloat(y) - 20;
		        ctx.moveTo(x, tmpY);
		        ctx.lineTo(x, y);
		        ctx.lineWidth = 3;
		        ctx.stroke();
			
		},
		
		initPolygon : function(canvas, canvasContainer, model, height, width){
			var imgSrc = jQuery('#'+canvasContainer).find('img').attr('src')
			if(imgSrc == undefined || imgSrc === 'data:jpeg;base64,'){
				return false
			}
			polyModel.setCanvasDimention(canvas, height, width);
			 var $ = go.GraphObject.make;
				try{
					polygonDiagram = $(go.Diagram, canvas);
				}catch(ex){}
				
			    polygonDiagram.toolManager.mouseDownTools.insertAt(3, new GeometryReshapingTool());
			    polygonDiagram.allowResize = true;
			    polygonDiagram.allowReshape = true;
			    polygonDiagram.allowRotate = true;
			    polygonDiagram.nodeTemplateMap.add("PolygonDrawing",
			        $(go.Node,
			            {locationSpot: go.Spot.Center},  // to support rotation about the center
			            new go.Binding("location", "loc", go.Point.parse).makeTwoWay(go.Point.stringify),
			            {
			                selectionAdorned: true, selectionObjectName: "SHAPE",
			                selectionAdornmentTemplate:  // custom selection adornment: a red rectangle
			                    $(go.Adornment, "Auto",
			                        $(go.Shape, {stroke: "red", fill: null}),
			                        $(go.Placeholder, {margin: -1}))
			            },
			            {resizable: true, resizeObjectName: "SHAPE"},
			            {rotatable: true, rotateObjectName: "SHAPE"},
			            {reshapable: true},  // GeometryReshapingTool assumes nonexistent Part.reshapeObjectName would be "SHAPE"
			            $(go.Shape,
			                {name: "SHAPE", fill: "red", strokeWidth: 1},
			                new go.Binding("desiredSize", "size", go.Size.parse).makeTwoWay(go.Size.stringify),
			                new go.Binding("angle").makeTwoWay(),
			                new go.Binding("geometryString", "geo").makeTwoWay(),
			                new go.Binding("fill"),
			                new go.Binding("stroke"),
			                new go.Binding("strokeWidth"))
			        ));
			    // create polygon drawing tool for myDiagram, defined in PolygonDrawingTool.js
			    var tool = new PolygonDrawingTool();

			    // provide the default JavaScript object for a new polygon in the model
			    tool.archetypePartData =
			        {fill: "rgba(255,0,0,0.3)", stroke: "red", strokeWidth: 1.5, category: "PolygonDrawing"};
			    tool.isPolygon = true;  // for a polyline drawing tool set this property to false
			    tool.isEnabled = true;
			    // install as first mouse-down-tool
			    polygonDiagram.toolManager.mouseDownTools.insertAt(0, tool);
			    polygonDiagram.allowHorizontalScroll = false;
			    polygonDiagram.allowVerticalScroll = false;
				console.log('load');
			    //this.loadPolygon();  // load a simple diagram from the textarea
				polyModel.load(model)
			    polygonDiagram.currentTool = tool;
		},
		
		load : function(model, reset){
			 let json = {};
			    if (!reset) {
			        const polygonData = document.getElementById(model).value;
			        try{
			        	 json = polygonData && polygonData !== '' ? JSON.parse(polygonData) : {};
			        }catch(e){
			        }       
			    }
			    try {
			        polygonDiagram.initialPosition = go.Point.parse(json.position ? json.position : "0 0");
			        polygonDiagram.model = go.Model.fromJson(json.model ? json.model : {});
			        polygonDiagram.model.undoManager.isEnabled = true;
			    } catch (error) {
			        alert('Failed to load Polygon model')
			    }
		},
		save: function(model, polygon){
			  const polygonData = document.getElementById(model);
		    if (polygonDiagram.model.nodeDataArray.length > 0) {
		    	document.getElementById(polygon).value = polygonDiagram.model.nodeDataArray.length;
		    	polyModel.updatePosition(polygonDiagram.model.nodeDataArray);
		        polygonData.value = '{ "position": "' + go.Point.stringify(polygonDiagram.position) + '",\n  "model": ' + polygonDiagram.model.toJson() + ' }';    
		    }else  {
		    	document.getElementById(polygon).value = 0;
		    	polygonData.value = '{}';    
		    }
			
		},
		
		updatePosition: function(ar){
			for(var i=0;i<ar.length;i++){
				try{
					var poly = ar[i];
					if(poly.loc && poly.pointLoc !== poly.loc){				
						var loc = poly.loc.split(" ");
						var pointLoc = poly.pointLoc.split(" ");		
						var val = polyModel.getDiff(pointLoc[0], loc[0])
						polyModel.updatePoint(poly.pointX, val)
						val = polyModel.getDiff(pointLoc[1], loc[1])
						polyModel.updatePoint(poly.pointY, val)
						poly.pointLoc = poly.loc;
					}
					
					if(poly.size && poly.pointSize !== poly.size ){
						var size = poly.size.split(" ");
						var pointSize = poly.pointSize.split(" ");
						var diff =  polyModel.getDiff(pointSize[0], size[0])				
						polyModel.updateSize(poly.pointX, diff, polyModel.getSize(poly.pointX))	
						
						diff =  polyModel.getDiff(pointSize[1], size[1])
						polyModel.updateSize(poly.pointY, diff, polyModel.getSize(poly.pointY))
						
						poly.pointSize = poly.size;
					}
					
					if(poly.angle && poly.angle !== poly.pointAngle ){
						polyModel.rotatePolygon(poly, poly.angle)
						poly.pointAngle = poly.angle;
					}
					
				}catch(e){
					
				}
			}	
		},		
		getDiff : function(oldVal, newVal){
			var a = parseFloat(oldVal);
			var b = parseFloat(newVal);
			return b - a;
		},
		getPositiveDiff : function(oldVal, newVal){
			var a = parseFloat(oldVal);
			var b = parseFloat(newVal);
			var val = b-a;
			return val <0 ? -val : val;
		},
		getSize : function(ar){		
			return Math.max.apply(Math, ar) - Math.min.apply(Math, ar);
		},
		getCenter : function(ar){	
			 var min = Math.min.apply(Math, ar)
			 var size = polyModel.getSize(ar)
			return min + (size/2) 
		},
		updatePoint : function(array, val){
			for(var i=0;i<array.length;i++){
				array[i] = array[i] + val;
			}
		},
		updateSize : function(array, diff, size){			
			 var val = diff/2;
			 var center = polyModel.getCenter(array);			 
			 for(var i=0;i<array.length;i++){
				 var p = array[i];
					 if(p <= center){
						 p = p-val;
					 }else{
						 p = p+val;  
					 }
				 array[i] =p;
				}
		},
		rotatePolygon : function(poly, angle){
			 var cX = polyModel.getCenter(poly.pointX);
			 var cY = polyModel.getCenter(poly.pointY);
			 for(var i=0;i<poly.pointX.length;i++){			 
				 var p = polyModel.rotate(cX,cY,poly.pointX[i], poly.pointY[i], angle)
				 poly.pointX[i] = p[0];
				 poly.pointY[i] = p[1];
				 
			 }		 
		},
		rotate : function(cx, cy, x, y, angle) {
		    var radians = (Math.PI / 180) * angle,
	        cos = Math.cos(radians),
	        sin = Math.sin(radians),
	        nx = (cos * (x - cx)) + (sin * (y - cy)) + cx,
	        ny = (cos * (y - cy)) - (sin * (x - cx)) + cy;
		    return [nx, ny];
		}
}

/**** FIN MOH ****/



