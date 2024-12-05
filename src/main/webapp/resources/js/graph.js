window.onload = () => {
    setR(4);
};
let R;
function setR(Rvalue){
    R = Rvalue;
    updateScale(R);
}


function updateScale(Rvalue) {
    console.log(Rvalue);
    document.getElementById('blockId').innerHTML=
        `<svg xmlns="http://www.w3.org/2000/svg" width="360" height="360" id = "svgId">
        <!-- shapes-->
            <rect x=${180 - 30 * Rvalue} y="180" width=${30 * Rvalue} height=${30 * Rvalue} class="geometryShape" id = "rect"/>
            <polygon points="${180},${180 + 15 * Rvalue} ${180},${180} ${180 + 30 * Rvalue},${180}" class="geometryShape" id = "tri"/>
            <path d="M${180 - 30 * Rvalue},${180} A${30 * Rvalue},${30 * Rvalue} ${0} ${0},${1} ${180},${180 - 30 * Rvalue} L${180},${180} Z" class="geometryShape" id = "circle"/>
        
            <!-- X axis -->
            <line stroke="black" x1="0" x2="360" y1="180" y2="180" />
            <line stroke="black" x1="350" x2="360" y1="175" y2="180" />
            <line stroke="black" x1="350" x2="360" y1="185" y2="180" />
            <text fill="black" x="348" y="170">X</text>
        
            <!-- Y axis -->
            <line stroke="black" x1="180" x2="180" y1="0" y2="360" />
            <line stroke="black" x1="180" x2="185" y1="0" y2="10" />
            <line stroke="black" x1="180" x2="175" y1="0" y2="10" />
            <text fill="black" x="190" y="12">Y</text>
        
            <!-- X points -->
            <line stroke="black" x1=${180-30*Rvalue} x2=${180-30*Rvalue} y1="183" y2="177"/>
            <line stroke="black" x1=${180-15*Rvalue} x2=${180-15*Rvalue} y1="183" y2="177"/>
            <line stroke="black" x1=${180+15*Rvalue} x2=${180+15*Rvalue} y1="183" y2="177"/>
            <line stroke="black" x1=${180+30*Rvalue} x2=${180+30*Rvalue} y1="183" y2="177"/>
        
            <!-- X points R-->
            <text fill="black" x=${180-30*Rvalue} y="170" font-size="8">-R</text>
            <text fill="black" x=${180-15*Rvalue} y="170" font-size="8">-R/2</text>
            <text fill="black" x=${180+15*Rvalue} y="170" font-size="8">R/2</text>
            <text fill="black" x=${180+30*Rvalue} y="170" font-size="8">R</text>
        
            <!-- Y points -->
            <line stroke="black" x1="177" x2="183" y1=${180-30*Rvalue} y2=${180-30*Rvalue} id = "YlR"/>
            <line stroke="black" x1="177" x2="183" y1=${180-15*Rvalue} y2=${180-15*Rvalue} id = "YlR2"/>
            <line stroke="black" x1="177" x2="183" y1=${180+15*Rvalue} y2=${180+15*Rvalue} id = "YlmR2"/>
            <line stroke="black" x1="177" x2="183" y1=${180+30*Rvalue} y2=${180+30*Rvalue} id = "YlmR"/>
        
            <!-- Y points R-->
            <text fill="black" x="190" y=${180-30*Rvalue} font-size="8">R</text>
            <text fill="black" x="190" y=${180-15*Rvalue} font-size="8">R/2</text>
            <text fill="black" x="190" y=${180+15*Rvalue} font-size="8">-R/2</text>
            <text fill="black" x="190" y=${180+30*Rvalue} font-size="8">-R</text>
        </svg>`;
    updateDots();

}

function svgMousePosition() {
    let c = 0;

    const imgBox = document.getElementById("ImgBox");
    // Убедимся, что старые слушатели не мешают
    imgBox.onclick = null;

    imgBox.addEventListener("click", function (event) {
        c += 1;
        const rect = this.getBoundingClientRect(); // Точные границы элемента
        const x = event.clientX - rect.left -5;      // Расчет X относительно элемента
        const y = event.clientY - rect.top;       // Расчет Y относительно элемента
        let transformedX = Math.round((x - 180)*100 / (30))/100
        let transformedY = Math.round(-(y - 180)*100 / (30))/100

        console.log("X:" + x + " Y:" + y + " c:" + c+" Rvalue:"+R);
        console.log("TX:" + transformedX + " TY:" + transformedY);
        document.getElementById('hiddenForm:hiddenX').value = transformedX;
        document.getElementById('hiddenForm:hiddenY').value = transformedY;
        document.getElementById('hiddenForm:hiddenR').value = R;

        savePoints();

    });
}
function drawDots(data) {
    console.log("Полученные данные:", data);



    // Проверяем, что данные — это массив
    const points = typeof data === "string" ? JSON.parse(data) : data;

    if (!Array.isArray(points)) {
        console.error("Ошибка: данные не являются массивом.", points);
        return;
    }

    // Далее ваш существующий код
    const svg = document.getElementById("svgId");
    svg.querySelectorAll("circle").forEach((circle) => circle.remove());

    points.forEach((point) => {
        console.log(`x=${point.x}, y=${point.y}`);
        let x = (point.x * 100 * (30)) / 100 + 180
        let y = (-1 * point.y * 100 * (30)) / 100 + 180
        console.log(`###x=${x}, ###y=${y}`);
        const circle = document.createElementNS("http://www.w3.org/2000/svg", "circle");
        circle.setAttribute("cx", x);
        circle.setAttribute("cy", y);
        circle.setAttribute("r", 5);
        circle.setAttribute("fill", colorSetter(point.status));
        circle.setAttribute("stroke", "black");
        circle.setAttribute("stroke-width", "1");
        svg.appendChild(circle);
    });
    console.log("####################################################################################################")
}
function colorSetter(status){
    if (status){
        return "green"
    }else {
        return "red"
    }
}