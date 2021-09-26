
var poligonos=0;
var cursorPos;
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
			if(ansPoints){
				try{
				document.getElementById(ansPoints).value = '[]';
				}catch (e) {
				}
			}
		    
			var img = new Image();
	        img.setAttribute('src', imgSrc);
	        img.addEventListener('load', (e) => {
	            paintingCanvasID.width = img.width;
	            paintingCanvasID.height = img.height;
	            ctx = paintingCanvasID.getContext("2d");
	            ctx.drawImage(img, 0, 0, paintingCanvasID.width, paintingCanvasID.height);

	        });
	        paintingCanvasID = document.getElementById(canvas);
         	paintingCanvasID.width = document.getElementById(width).value;
         	paintingCanvasID.height = document.getElementById(height).value;
         	
		    ctx = paintingCanvasID.getContext("2d");
		    ctx.clearRect(0, 0, paintingCanvasID.width, paintingCanvasID.height);
		    
		    cursorPos = cursor;
		    paintingCanvasID.addEventListener("mousemove", function (e, cursor) {
	            var cRect = paintingCanvasID.getBoundingClientRect(); // Gets CSS pos, and width/height
	            var canvasX = Math.round(e.clientX - cRect.left); // Subtract the 'left' of the canvas
	            var canvasY = Math.round(e.clientY - cRect.top); // from the X/Y positions to make
	            document.getElementById(cursorPos).value = "X: " + canvasX + ", Y: " + canvasY;
	        });
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
		
		clearPoint : function(canvas, canvasContainer, height, width, cursor, ansPoints, scorePoints){
			let paintingCanvasID = document.getElementById(canvas);
		    ctx = paintingCanvasID.getContext("2d");
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


