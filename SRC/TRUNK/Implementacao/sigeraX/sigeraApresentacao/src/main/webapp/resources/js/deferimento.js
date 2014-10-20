function modifiqueVisualizacaoControlesDeferimento(idControleMostrar, idControleEsconder) {
    $(document.getElementById(idControleMostrar)).show("drop");
    $(document.getElementById(idControleEsconder)).hide("drop");    
}

function mostreControlesDeferimento() {   
    modifiqueVisualizacaoControlesDeferimento('controles-parecer', 'mostrar-parecer');
}

function escondaControlesDeferimento() {    
    modifiqueVisualizacaoControlesDeferimento('mostrar-parecer', 'controles-parecer');
}

function mostreControlesConferir() {   
    modifiqueVisualizacaoControlesDeferimento('controles-parecer-assinatura', 'mostrar-parecer-assinatura');
}

function escondaControlesConferir() {    
    modifiqueVisualizacaoControlesDeferimento('mostrar-parecer-assinatura', 'controles-parecer-assinatura');
}


function mostreControlesDeferimentoAcerto() {   
    modifiqueVisualizacaoControlesDeferimento('controles-parecer-acerto', 'mostrar-parecer-acerto');
}

function escondaControlesDeferimentoAcerto() {    
    modifiqueVisualizacaoControlesDeferimento('mostrar-parecer-acerto', 'controles-parecer-acerto');
}


function mostreControlesProrrogacao() {   
    modifiqueVisualizacaoControlesDeferimento('controles-parecer-prorrogacao', 'mostrar-parecer-prorrogacao');
}

function escondaControlesProrrogacao() {   
    modifiqueVisualizacaoControlesDeferimento('mostrar-parecer-prorrogacao', 'controles-parecer-prorrogacao');
}

function mostreControlesDelegarProrrogacao() {   
    modifiqueVisualizacaoControlesDeferimento('controles-parecer-delegacao', 'mostrar-parecer');
}

function escondaControlesDelegarProrrogacao() {   
    modifiqueVisualizacaoControlesDeferimento('mostrar-parecer', 'controles-parecer-delegacao');
}


