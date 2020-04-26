
var Px = 0;
var Py = 0;
var mapa10x10 = [];
var myGame;
var turno = 1;
var damage = 0;
var atacaJ = 1;
var finbatalla = 0;
var cObjeto = 0;
var enbatalla = 0;

// -------------------------------------------------------------------------- //
// Genera nova partida
function newGame(){
  turno = 1;
  damage = 0;
  atacaJ = 1;
  finbatalla = 1;
  cObjeto = 0;
  enbatalla = 0;

  tryAJAXSGet("Nueva", function(response){
    myGame = JSON.parse(response);
    loadGame();
    var nombre = prompt("NEW GAME\nEnter your name:");
    var sexo = prompt("Enter your sex:");
    myGame.player.nombre = nombre + "/" + sexo;
    updateStats();
    myGame.enemigo.vida = 10*parseInt(myGame.player.nivel);
    myGame.kylo.vida = 50;
    myGame.player.bajas = 0;
    updateCounter();
    musicat = document.getElementById("musicatheme");
    musicat.src="./media/sound/StarWarsTheme.mp3";
    musicat.play();
    
  });
}

// -------------------------------------------------------------------------- //
// Genera partida slot1
function loadSlot1(){
  tryAJAXSGet("1", function(response){
    myGame = JSON.parse(response);
    loadGame();
  });
}

// -------------------------------------------------------------------------- //
// Genera partida slot2
function loadSlot2(){
  tryAJAXSGet("2", function(response){
    myGame = JSON.parse(response);
    loadGame();
  });
}

// -------------------------------------------------------------------------- //
// Inicia minimapa i canvas per defecte
function iniciarJuego(){
  turno = 1;
  damage = 0;
  atacaJ = 1;
  finbatalla = 1;
  cObjeto = 0;
  enbatalla = 0;

  var paras = document.getElementsByClassName('casilla');
  while(paras[0]){
    paras[0].parentNode.removeChild(paras[0]);
  }
  for(i  = 0; i < 10; i++){
    for(j = 0; j < 10; j++){
      var div = document.createElement("div");
      div.style.width = "10px";
      div.style.height = "10px";
      div.id="f"+i+"c"+j;
      div.setAttribute("class", "casilla");
      div.style.backgroundColor = 'rgb(0, 0, 0)';
      div.style.border = '3px solid rgb(0, 0, 0)';
      map.append(div);
      var c = document.getElementById("visor");
      var ctx = c.getContext("2d");
      ctx.clearRect(0,0,481,416);
      var img = new Image();
      img.src = './media/images/test.png';
      img.onload = function () {
        // Pinta imagen en el canvas
        ctx.drawImage(this, 50, 50);
      }
    }
  }
}


function loadGame() {
  //Removes previous minimap
  var paras = document.getElementsByClassName('casilla');
  while(paras[0]){
    paras[0].parentNode.removeChild(paras[0]);
  }

  console.log(myGame);
  updateStats();
  updateCounter();

  var columnas = 10;
  var index = 0;
  var map = document.getElementById("map");

  for(i = 0; i < 10; i++){
    mapa10x10[i]= new Array(columnas);

    for(j = 0; j < 10; j++){
      mapa10x10[i][j]= myGame.map[myGame.lvl][index];
      index +=1;
      if(mapa10x10[i][j] == 'S'){
        Px = i;
        Py = j;
        //console.log("Px ="+Px+"Py ="+Py);
        player.estadoPartida.x = Px;
        player.estadoPartida.y = Py;
        player.estadoPartida.direccion = myGame.player.estadoPartida.direccion;
      }
    }
  }

  switch(myGame.player.estadoPartida.direccion){
    case 0:
      pintaPosicion(Px-1,Py);
      break;
    case 1:
      pintaPosicion(Px+1,Py);
      break;
    case 2:
      pintaPosicion(Px,Py+1);
      break;
    case 3:
      pintaPosicion(Px,Py-1);
      break;
    default:
    pintaPosicion(Px,Py-1);
    break;
  }

  // console.log(mapa10x10);
  // Minimapa
  for(i  = 0; i < 10; i++){

    for(j = 0; j < 10; j++){

      var div = document.createElement("div");
      div.style.width = "10px";
      div.style.height = "10px";
      div.id="f"+i+"c"+j;
      div.setAttribute("class", "casilla");
      if ((i == Px - 1 || i == Px + 1 || i == Px) && (j == Py - 1 || j == Py + 1 || j == Py)) {
        if (mapa10x10[i][j] == 'P') {
          div.style.backgroundColor = 'rgb(130, 130, 130)';
          div.style.border = "3px solid rgb(130, 130, 130)";
        } else {
          div.style.border = "3px solid rgb(215, 215, 215)";

        }
        if (mapa10x10[i][j] == 'EN') {
          div.style.backgroundColor = 'rgb(237, 108, 100)';
        }
        if (mapa10x10[i][j] == 'ENK') {
          div.style.backgroundColor = 'rgb(249, 233, 0)';
        }
        if (mapa10x10[i][j] == 'N') {
          div.style.backgroundColor = 'rgb(215, 215, 215)';
          div.style.backgroundImage= "";
        }
        if (mapa10x10[i][j] == 'BL' || mapa10x10[i][j] == 'KS'|| mapa10x10[i][j] == 'EL') {
          div.style.backgroundColor = 'rgb(0, 204, 0)';
        }
        if (mapa10x10[i][j] == 'S') {
          div.style.backgroundColor = 'rgb(215, 215, 215)';
          /* 0 Norte, 1 Sud, 2 Este, 3 Oeste*/
          if(player.estadoPartida.direccion == 3){
            div.style.backgroundImage= "url('media/direccion_personaje/esquerra.png')";

          }else{
            if(player.estadoPartida.direccion == 2){
              div.style.backgroundImage= "url('media/direccion_personaje/dreta.png')";
            }else{
              if(player.estadoPartida.direccion == 1){
                div.style.backgroundImage= "url('media/direccion_personaje/endarrere.png')";
              }else{
                if(player.estadoPartida.direccion == 0){
                  div.style.backgroundImage= "url('media/direccion_personaje/endavant.png')";
                }
              }
            }
          }
        }
        if (mapa10x10[i][j] == 'PE') {
          div.style.backgroundColor = 'rgb(163, 113, 66)';
        }
        if (mapa10x10[i][j] == 'PS') {
          div.style.backgroundColor = 'rgb(113, 147, 72)';
        }
      } else {
        div.style.backgroundColor = 'rgb(0, 0, 0)';
        div.style.border = '3px solid rgb(0, 0, 0)';
      }
      map.append(div);
    }
  }

  //var cuadrao = document.getElementById(1);
  //cuadrao.style.backgroundColor = '#000000';
}

/* Convierte lo que hay en el mapa en un archivo de imagen */
function mapaToImg(x, y) {
  /* TODO */
    if(mapa10x10[x][y] == 'P' ){
      return "dungeon_wall.png";
    }
    if(mapa10x10[x][y] == 'N' || mapa10x10[x][y] == 'S'){
      return "dungeon_step.png";
    }
    if(mapa10x10[x][y] == 'EN'){
      return "dungeon_enemy.png";
    }
    if(myGame.lvl == 0 && mapa10x10[x][y] != 'EN'){
      if(mapa10x10[x][y] == 'ENK'){
        return "dungeon_kylo.png";
      }
    } else if(myGame.lvl == 1 ){
      if(mapa10x10[x][y] == 'ENK'){
        return "dungeon_darth.png";
      }
    }

    if(mapa10x10[x][y] == 'PE' ||mapa10x10[x][y] == 'PS'){
      return "dungeon_door.png";
    }
    if(mapa10x10[x][y] == 'BL'){
      return "dungeon_blaster.png";
    }
    if(myGame.lvl == 0 && mapa10x10[x][y] != 'BL'){
      if(mapa10x10[x][y] == 'KS'){
        return "dungeon_kylo_sword.png";
      }
    } else if(myGame.lvl == 1){
      if(mapa10x10[x][y] == 'EL'){
        return "dungeon_espada_laser.png";
      }
    }
}

function teclado(direccion){
  var win = 1;
  switch(direccion.id){

    case"movesquerra":
    /* 0 Norte, 1 Sud, 2 Este, 3 Oeste*/
    if(player.estadoPartida.direccion == 3 && enbatalla != 1){
      player.estadoPartida.direccion = 1;
      myGame.player.estadoPartida.direccion = 1;
      pintaPosicion(Px+1,Py);
      console.log("PX="+Px+"PY="+Py);
      console.log("Direccion"+player.estadoPartida.direccion);
    }else{
      if(player.estadoPartida.direccion == 1 && enbatalla != 1){
        player.estadoPartida.direccion = 2;
        myGame.player.estadoPartida.direccion = 2;
        pintaPosicion(Px,Py+1);
        console.log("PX="+Px+"PY="+Py);
        console.log("Direccion"+player.estadoPartida.direccion);
      }else{
        if(player.estadoPartida.direccion == 2 && enbatalla != 1){
          player.estadoPartida.direccion = 0;
          myGame.player.estadoPartida.direccion = 0;
          pintaPosicion(Px-1,Py);
          console.log("PX="+Px+"PY="+Py);
          console.log("Direccion"+player.estadoPartida.direccion);
        }else{
          if(player.estadoPartida.direccion == 0 && enbatalla != 1){
            player.estadoPartida.direccion = 3;
            myGame.player.estadoPartida.direccion = 3;
            pintaPosicion(Px,Py-1);
            console.log("PX="+Px+"PY="+Py);
            console.log("Direccion"+player.estadoPartida.direccion);
          }
        }
      }
    }
    break;

    case"movdreta":
    /* 0 Norte, 1 Sud, 2 Este, 3 Oeste*/
    if(player.estadoPartida.direccion == 3 && enbatalla != 1){
      player.estadoPartida.direccion = 0;
      myGame.player.estadoPartida.direccion = 0;
      pintaPosicion(Px-1,Py);
      console.log("PX="+Px+"PY="+Py);
    } else{
      if(player.estadoPartida.direccion == 0 && enbatalla != 1){
        player.estadoPartida.direccion = 2;
        myGame.player.estadoPartida.direccion = 2;
        pintaPosicion(Px,Py+1);
      }else{
        if(player.estadoPartida.direccion == 2 && enbatalla != 1){
          player.estadoPartida.direccion = 1;
          myGame.player.estadoPartida.direccion = 1;
          pintaPosicion(Px+1,Py);
        }else{
          if(player.estadoPartida.direccion == 1 && enbatalla != 1){
            player.estadoPartida.direccion = 3;
            myGame.player.estadoPartida.direccion = 3;
            pintaPosicion(Px,Py-1);
          }
        }
      }
    }

    break;

    case"movendavant":
    /* 0 Norte, 1 Sud, 2 Este, 3 Oeste*/
    //3 Oeste
    updateMinimap();
    if(player.estadoPartida.direccion == 3){
      if(mapa10x10[Px][Py-1] != 'P'){
        if (mapa10x10[Px][Py-1] == 'PS') {
          console.log("change nivell");
          respuesta = prompt("Quieres subir (S/N)");
          if (respuesta == 'S'){
            changeMap(1);
            return;
          } else {
            return;
          }
        }
        if (mapa10x10[Px][Py-1] == 'PE') {
          console.log("change nivell");
          respuesta = prompt("Quieres bajar? (S/N)");
          if (respuesta == 'S'){
            changeMap(-1);
            return;
          } else  {
            return;
          }
        }

        //Si no hay enemigo avanza normal
        if ( mapa10x10[Px][Py-1] != 'EN' && mapa10x10[Px][Py-1] != 'ENK'&& mapa10x10[Px][Py-1] != 'BL' && mapa10x10[Px][Py-1] != 'KS'&& mapa10x10[Px][Py-1] != 'EL') {
          var yAux = mapa10x10[Px][Py-1];
          mapa10x10[Px][Py-1] = mapa10x10[Px][Py];
          mapa10x10[Px][Py] = yAux;
          Py = Py-1;
          pintaPosicion(Px,Py-1);
        } else
        //Si hay enemigo, hay batalla
        if (mapa10x10[Px][Py-1] == 'EN' && finbatalla == 1 || mapa10x10[Px][Py-1] == 'ENK' && finbatalla == 1) {
          batalla(Px,Py-1);
          console.log("BL??"+mapa10x10[Px][Py-1] );
        }else if(mapa10x10[Px][Py-1] == 'BL'|| mapa10x10[Px][Py-1] == 'KS' || mapa10x10[Px][Py-1] != 'EL'){
          cogerObjeto(Px,Py-1);
          if(cObjeto == 1 ){
            console.log("YES");
            mapa10x10[Px][Py-1] = mapa10x10[Px][Py];
            mapa10x10[Px][Py] = 'N';
            Py = Py-1;
            pintaPosicion(Px,Py);
          } else{
            console.log("NO");
            mapa10x10[Px][Py-1] = mapa10x10[Px][Py];
            mapa10x10[Px][Py] = 'N';
            Py = Py-1;
            pintaPosicion(Px,Py);
          }
        }
        if(myGame.player.vida <= 0){
          /*myGame.player.vida = 10*parseInt(myGame.player.nivel);
          myGame.enemigo.vida = 10*parseInt(myGame.player.nivel);
          finbatalla = 1;
          turno = 1;
          atacaJ = 1;
          mapa10x10[Px][Py] = 'N';
          mapa10x10[4][2] = 'S';
          var div = document.getElementById("f"+Px+"c"+Py);
          div.style.backgroundImage= "";
          Px = 4;
          Py = 2;
          player.estadoPartida.direccion = 3;
          myGame.player.estadoPartida.direccion = 3;
          updateStats();
          pintaPosicion(Px,Py);
          player.estadoPartida.x = Px;
          player.estadoPartida.y = Py;
        } else{
          pintaPosicion(Px,Py-1);
          player.estadoPartida.x = Px;
          player.estadoPartida.y = Py;*/
          enbatalla = 0;
          finbatalla = 0;
          var c = document.getElementById("visor");
          var ctx = c.getContext("2d");
          ctx.clearRect(0,0,481,416);
          iniciarJuego();
          newGame();
        }
        pintaPosicion(Px,Py-1);
      }
    } else{
      //Sud
      if(player.estadoPartida.direccion == 1){
        if(mapa10x10[Px+1][Py] != 'P'){
          if (mapa10x10[Px+1][Py] == 'PS') {
            console.log("change nivell");
            respuesta = prompt("Quieres subir (S/N)");
            if (respuesta == 'S'){
              changeMap(1);
              return;
            } else {
              return;
            }
          }
          if (mapa10x10[Px+1][Py] == 'PE') {
            console.log("change nivell");
            respuesta = prompt("Quieres bajar? (S/N)");
            if (respuesta == 'S'){
              changeMap(-1);
              return;
            } else  {
              return;
            }
          }
          //Si no hay enemigo avanza normal
          if ( mapa10x10[Px+1][Py] != 'EN' && mapa10x10[Px+1][Py] != 'ENK'&& mapa10x10[Px+1][Py] != 'BL'&& mapa10x10[Px+1][Py] != 'KS'&& mapa10x10[Px+1][Py] != 'EL') {
            var yAux = mapa10x10[Px+1][Py];
            mapa10x10[Px+1][Py] = mapa10x10[Px][Py];
            mapa10x10[Px][Py] = yAux;
            Px = Px+1;
            pintaPosicion(Px+1,Py);
          } else
          //Si hay enemigo, hay batalla
          if (mapa10x10[Px+1][Py] == 'EN' && finbatalla == 1|| mapa10x10[Px+1][Py] == 'ENK'&& finbatalla == 1 ) {
            batalla(Px+1,Py);

          }else if(mapa10x10[Px+1][Py] == 'BL' || mapa10x10[Px+1][Py] == 'KS'|| mapa10x10[Px+1][Py] == 'EL'){
            cogerObjeto(Px+1,Py);
            if(cObjeto == 1 ){
              console.log("YES");
              mapa10x10[Px+1][Py] = mapa10x10[Px][Py];
              mapa10x10[Px][Py] = 'N';
              Px = Px+1;
              pintaPosicion(Px,Py);
            } else{
              console.log("NO");
              mapa10x10[Px+1][Py] = mapa10x10[Px][Py];
              mapa10x10[Px][Py] = 'N';
              Px = Px+1;
              pintaPosicion(Px,Py);
            }
          }
          if(myGame.player.vida <= 0){
           /* myGame.player.vida = 10*parseInt(myGame.player.nivel);
            myGame.enemigo.vida = 10*parseInt(myGame.player.nivel);
            finbatalla = 1;
            turno = 1;
            atacaJ = 1;
            mapa10x10[Px][Py] = 'N';
            mapa10x10[4][2] = 'S';
            var div = document.getElementById("f"+Px+"c"+Py);
            div.style.backgroundImage= "";
            Px = 4;
            Py = 2;
            player.estadoPartida.direccion = 3;
            myGame.player.estadoPartida.direccion = 3;
            updateStats();
            pintaPosicion(Px,Py);
            player.estadoPartida.x = Px;
            player.estadoPartida.y = Py;
          } else{
            pintaPosicion(Px+1,Py);
            player.estadoPartida.x = Px;
            player.estadoPartida.y = Py;*/
            enbatalla = 0;
            finbatalla = 0;
            var c = document.getElementById("visor");
            var ctx = c.getContext("2d");
            ctx.clearRect(0,0,481,416);
            iniciarJuego();
            newGame();
          }
        }
        pintaPosicion(Px+1,Py);
      } else{
        //Este
        if(player.estadoPartida.direccion == 2){
          if(mapa10x10[Px][Py+1] != 'P'){
            if (mapa10x10[Px][Py+1] == 'PS') {
              console.log("change nivell");
              respuesta = prompt("Quieres subir (S/N)");
              if (respuesta == 'S'){
                changeMap(1);
                return;
              } else {
                return;
              }
            }
            if (mapa10x10[Px][Py+1] == 'PE') {
              console.log("change nivell");
              respuesta = prompt("Quieres bajar? (S/N)");
              if (respuesta == 'S'){
                changeMap(-1);
                return;
              } else  {
                return;
              }
            }
           //Si no hay enemigo avanza normal
          if ( mapa10x10[Px][Py+1] != 'EN' && mapa10x10[Px][Py+1] != 'ENK'&& mapa10x10[Px][Py+1] != 'BL' && mapa10x10[Px][Py+1] != 'KS'&& mapa10x10[Px][Py+1] != 'EL') {
            var yAux = mapa10x10[Px][Py+1];
            mapa10x10[Px][Py+1] = mapa10x10[Px][Py];
            mapa10x10[Px][Py] = yAux;
            Py = Py+1;
            pintaPosicion(Px,Py+1);
          }else
            //Si hay enemigo, hay batalla
            if (mapa10x10[Px][Py+1] == 'EN'&& finbatalla == 1 || mapa10x10[Px][Py+1] == 'ENK'&& finbatalla == 1 ) {
              console.log("batalla este");
              batalla(Px,Py+1);

            }else if(mapa10x10[Px][Py+1] == 'BL' || mapa10x10[Px][Py+1] == 'KS'|| mapa10x10[Px][Py+1] == 'EL'){
              cogerObjeto(Px,Py+1);
              if(cObjeto == 1 ){
                console.log("YES");
                mapa10x10[Px][Py+1] = mapa10x10[Px][Py];
                mapa10x10[Px][Py] = 'N';
                Py = Py+1;
                pintaPosicion(Px,Py);
              } else{
                console.log("NO");
                mapa10x10[Px][Py+1] = mapa10x10[Px][Py];
                mapa10x10[Px][Py] = 'N';
                Py = Py+1;
                pintaPosicion(Px,Py);
              }
            }
            //Jugador ha perdido
            if(myGame.player.vida <= 0){
              /*myGame.player.vida = 10*parseInt(myGame.player.nivel);
              myGame.enemigo.vida = 10*parseInt(myGame.player.nivel);
              finbatalla = 1;
              turno = 1;
              atacaJ = 1;
              mapa10x10[Px][Py] = 'N';
              if(myGame.lvl == 1){
                mapa10x10[4][2] = 'S';
              } else if (myGame.lvl == 0){
                mapa10x10[7][8] = 'S';
              var div = document.getElementById("f"+Px+"c"+Py);
              div.style.backgroundImage= "";
              Px = 4;
              Py = 2;
              player.estadoPartida.direccion = 3;
              myGame.player.estadoPartida.direccion = 3;
              updateStats();
              pintaPosicion(Px,Py);
              player.estadoPartida.x = Px;
              player.estadoPartida.y = Py;*/
              var c = document.getElementById("visor");
              var ctx = c.getContext("2d");
              ctx.clearRect(0,0,481,416);
              enbatalla = 0;
              finbatalla = 0;
              iniciarJuego();
              newGame();
            }
          }
          pintaPosicion(Px,Py+1);
        } else{
          //Norte
          if(player.estadoPartida.direccion == 0){
            if(mapa10x10[Px-1][Py] != 'P'){
              if (mapa10x10[Px-1][Py] == 'PS') {
                console.log("change nivell");
                respuesta = prompt("Quieres subir (S/N)");
                if (respuesta == 'S'){
                  changeMap(1);
                  return;
                } else {
                  return;
                }
              }
              if (mapa10x10[Px-1][Py] == 'PE') {
                console.log("change nivell");
                respuesta = prompt("Quieres bajar? (S/N)");
                if (respuesta == 'S'){
                  changeMap(-1);
                  return;
                } else  {
                  return;
                }
              }
              //Si no hay enemigo avanza normal o objeto
              if ( mapa10x10[Px-1][Py] != 'EN' && mapa10x10[Px-1][Py] != 'ENK'&& mapa10x10[Px-1][Py] != 'BL'&& mapa10x10[Px-1][Py] != 'KS'&& mapa10x10[Px-1][Py] != 'EL') {
                var yAux = mapa10x10[Px-1][Py];
                mapa10x10[Px-1][Py] = mapa10x10[Px][Py];
                mapa10x10[Px][Py] = yAux;
                Px = Px-1;
                pintaPosicion(Px,Py);
              } else
              //Si hay enemigo, hay batalla
              console.log("finbatalla =="+finbatalla);
              if (mapa10x10[Px-1][Py] == 'EN' && finbatalla == 1|| mapa10x10[Px-1][Py] == 'ENK'&& finbatalla == 1) {
                console.log("ENTRA AQUI");
                batalla(Px-1,Py);

              }else if(mapa10x10[Px-1][Py] == 'BL' || mapa10x10[Px-1][Py] == 'KS'|| mapa10x10[Px-1][Py] == 'EL'){
                cogerObjeto(Px-1,Py);
                if(cObjeto == 1 ){
                  console.log("YES");
                  mapa10x10[Px-1][Py] = mapa10x10[Px][Py];
                  mapa10x10[Px][Py] = 'N';
                  Px = Px-1;
                  pintaPosicion(Px,Py);
                } else{
                  console.log("NO");
                  mapa10x10[Px-1][Py] = mapa10x10[Px][Py];
                  mapa10x10[Px][Py] = 'N';
                  Px = Px-1;
                  pintaPosicion(Px,Py);
                }
              }
              if(myGame.player.vida <= 0){
                /*myGame.player.vida = 10*parseInt(myGame.player.nivel);
                myGame.enemigo.vida = 10*parseInt(myGame.player.nivel);
                finbatalla = 1;
                turno = 1;
                atacaJ = 1;
                mapa10x10[Px][Py] = 'N';
                mapa10x10[4][2] = 'S';
                var div = document.getElementById("f"+Px+"c"+Py);
                div.style.backgroundImage= "";
                Px = 4;
                Py = 2;
                player.estadoPartida.direccion = 3;
                myGame.player.estadoPartida.direccion = 3;
                updateStats();
                pintaPosicion(Px,Py);
                player.estadoPartida.x = Px;
                player.estadoPartida.y = Py;*/
                enbatalla = 0;
                finbatalla = 0;
                var c = document.getElementById("visor");
                var ctx = c.getContext("2d");
                ctx.clearRect(0,0,481,416);
                iniciarJuego();
                newGame();
              }
              pintaPosicion(Px-1,Py);
            }

          }
        }
      }
    }
    break;
    case"movendarrere":
    /* 0 Norte, 1 Sud, 2 Este, 3 Oeste*/
    if(player.estadoPartida.direccion == 0 && enbatalla != 1){
      pintaPosicion(Px+1,Py);
      player.estadoPartida.direccion = 1;
      myGame.player.estadoPartida.direccion = 1;
    }else{
      if(player.estadoPartida.direccion == 1 && enbatalla != 1){
        pintaPosicion(Px-1,Py);
        player.estadoPartida.direccion =0;
        myGame.player.estadoPartida.direccion = 0;
      }else{
        if(player.estadoPartida.direccion == 2 && enbatalla != 1){
          pintaPosicion(Px,Py-1);
          player.estadoPartida.direccion =3;
          myGame.player.estadoPartida.direccion = 3;
        } else{
          if(player.estadoPartida.direccion == 3 && enbatalla != 1){
            pintaPosicion(Px,Py+1);
            player.estadoPartida.direccion =2;
            myGame.player.estadoPartida.direccion = 2;
          }
        }
      }
    }
    break;
  }
  updateMinimap();
}
//batalla entre enemigo y jugador
function batalla(x,y){
  enbatalla = 1;
  if(finbatalla == 1){
    console.log("Entra aqui 2");
    console.log();
    setTimeout(function(){var c = document.getElementById("visor");
    var ctx = c.getContext("2d");
    ctx.beginPath();
    ctx.rect(10, 330, 460, 70);
    ctx.fillStyle = '#ffffff';
    //Buscamos el ataque total del jugador
    var ataqueJ = myGame.player.ataque+myGame.player.manoizquierda.arma.ataque+myGame.player.manoderecha.arma.ataque;
    var defensaJ = myGame.player.defensa+myGame.player.manoizquierda.arma.defensa+myGame.player.manoderecha.arma.defensa;
 
    //Ataque y defensa del enemigo
    if(mapa10x10[x][y] == 'EN'){
      var ataqueE = parseInt(myGame.enemigo.ataque);
      var defensaE = parseInt(myGame.enemigo.defensa);
    } else {
      if(mapa10x10[x][y] == 'ENK'){
        var ataqueE = parseInt(myGame.kylo.ataque);
        var defensaE = parseInt(myGame.kylo.defensa);
      }
    }
    //Turno del Jugador
    if( atacaJ == 1 && finbatalla == 1){
      console.log("ATAQUE JUGADOR:"+ataqueJ);
      console.log("DEFENSA ENEMIGO:"+defensaE);
      tryAJAXAt(ataqueJ, defensaE,function(damage){
      console.log("DAMAGE JUGADOR:"+damage);
      if(mapa10x10[x][y] == 'EN'){
        console.log("Vida antes del ataque Enemigo:"+myGame.enemigo.vida);
      } else{
        console.log("Vida antes del ataque KYLO:"+myGame.kylo.vida);
      }
      if(damage >0){
        if(mapa10x10[x][y] == 'EN'){
          myGame.enemigo.vida = parseInt(myGame.enemigo.vida) - parseInt(damage) ;
        } else{
          if(mapa10x10[x][y] == 'ENK'){
            myGame.kylo.vida = parseInt(myGame.kylo.vida) - parseInt(damage) ;
          }
        }
      }
      if(mapa10x10[x][y] == 'EN'){
        console.log("Vida despues del ataque Enemigo:"+myGame.enemigo.vida);
      } else{
        if(mapa10x10[x][y] == 'ENK'){
          console.log("Vida despues del ataque KYLO:"+myGame.kylo.vida);
        }
      }
      //Enemigo con vida, mostramos su vida y el turno
      if(myGame.enemigo.vida > 0 && mapa10x10[x][y]== 'EN'){

        ctx.fill();
        ctx.font = "30px Arial";
        ctx.fillStyle= "black";
        ctx.fillText("Turno "+ turno +": Vida del enemigo "+myGame.enemigo.vida, 50, 375);
        ctx.stroke();
        if(damage > 0){
          ctx.font = "30px Arial";
          ctx.fillStyle= "red";
          ctx.fillText("-"+damage, 300, 140);
        } else{
          ctx.font = "30px Arial";
          ctx.fillStyle= "red";
          ctx.fillText("0", 300, 140);
        }
 
        turno = turno +1;
        atacaJ = 0;
        damage = 0;

      } else
        //Enemigo muere
        if (myGame.enemigo.vida <= 0 && mapa10x10[x][y]== 'EN'){
          setTimeout(function(){
          ctx.fill();
          ctx.font = "30px Arial";
          ctx.fillStyle= "black";
         ctx.fillText("Enemigo eleminado +"+myGame.enemigo.xp+"XP", 100, 375);
          ctx.stroke();
          if(damage > 0){
            ctx.font = "30px Arial";
            ctx.fillStyle= "red";
            ctx.fillText("-"+damage, 300, 140);
          } else{
            ctx.font = "30px Arial";
            ctx.fillStyle= "red";
            ctx.fillText("0", 300, 140);
          }
          },50);
          musica = document.getElementById("musica");
          musica.src="./media/sound/muerto.mp3";
          musica.play();
          myGame.enemigo.vida = 10*parseInt(myGame.player.nivel);
          finbatalla = 1;
          console.log("EXPERIENCIA QUE DA ENEMIGO"+myGame.enemigo.xp);
          myGame.player.xp += parseInt(myGame.enemigo.xp);
          console.log("EXPERIENCIA DE JUGADOR"+myGame.player.xp);
          updateStats();
          setTimeout(function(){updateLevel()},50);
          turno = 1;
          atacaJ = 1;
          dejarObjeto(x,y);
          enbatalla = 0;
          myGame.player.bajas += 1;
          updateCounter();
        }
        //Kylo Ren vivo
        if(myGame.kylo.vida > 0 && mapa10x10[x][y]== 'ENK'){

          ctx.fill();
          ctx.font = "30px Arial";
          ctx.fillStyle= "black";
          ctx.fillText("Turno "+turno+": Vida del enemigo "+myGame.kylo.vida, 50, 375);
          ctx.stroke();
          if(damage > 0){
            ctx.font = "30px Arial";
            ctx.fillStyle= "red";
            ctx.fillText("-"+damage, 300, 140);
          } else{
            ctx.font = "30px Arial";
            ctx.fillStyle= "red";
            ctx.fillText("0", 300, 140);
          }

          turno = turno +1;
          atacaJ = 0;
          damage = 0;
        } else
          //Kylo Ren muere
          if ( myGame.kylo.vida <= 0 && mapa10x10[x][y]== 'ENK'){
            setTimeout(function(){
            ctx.fill();
            ctx.font = "30px Arial";
            ctx.fillStyle= "black";
            if(myGame.lvl == 0){
              ctx.fillText("Kylo Ren eleminado", 100, 375);
            } else if( myGame.lvl == 1) {
              myGame.kylo.objetos[0].arma.nombre = "espada laser";
              ctx.fillText("Darth Vader eleminado", 100, 375);
            }
            
            ctx.stroke();
            if(damage > 0){
              ctx.font = "30px Arial";
              ctx.fillStyle= "red";
              ctx.fillText("-"+damage, 300, 140);
            } else{
              ctx.font = "30px Arial";
              ctx.fillStyle= "red";
              ctx.fillText("0", 300, 140);
            }
            },50);
            finbatalla = 1;
            musica = document.getElementById("musica");
            musica.src="./media/sound/muerto_kr.mp3";
            musica.play();
            myGame.player.xp += myGame.kylo.xp;
            updateStats();
            updateLevel();
            turno = 1;
            atacaJ = 1;
            dejarObjeto(x,y);
            enbatalla = 0;
            myGame.player.bajas += 1;
            updateCounter();
          }
      });//AJAX de ataque

    } else{
      //Ataca enemigo
      if(atacaJ == 0 &&finbatalla == 1){
        console.log("ATAQUE ENEMIGO:"+ataqueE);
        console.log("DEFENSA JUGADOR:"+defensaJ);
        tryAJAXAt(ataqueE, defensaJ,function(damage){
        console.log("DAMAGE ENEMIGO:"+damage);
        console.log("Vida antes del ataque Jugador:"+myGame.player.vida);
        if(damage >0){
          myGame.player.vida = parseInt(myGame.player.vida) - parseInt(damage) ;

        }
        //Jugador vivo
        if(myGame.player.vida > 0){

          ctx.fill();
          ctx.font = "30px Arial";
          ctx.fillStyle= "black";
          if(mapa10x10[x][y] == 'EN'){
            ctx.fillText("Turno "+turno+": Vida del enemigo "+myGame.enemigo.vida, 50, 375);
          } else{
            ctx.fillText("Turno "+turno+": Vida del enemigo "+myGame.kylo.vida, 50, 375);
          }
          if(damage > 0){
            ctx.font = "30px Arial";
            ctx.fillStyle= "yellow";
            ctx.fillText("Daño recibido "+damage, 140, 50);
            ctx.stroke();
          } else {
            ctx.font = "30px Arial";
            ctx.fillStyle= "yellow";
            ctx.fillText("Daño recibido 0", 140, 50);
            ctx.stroke();
          }

          turno = turno +1;
          damage = 0;
          atacaJ = 1;
          updateStats();
        }else{
          finbatalla = 0;
          atacaJ = 1;

        }

        }); //AJAX de ataque
      }
    }

    },50);

  }

}
function updateCounter() {
  document.getElementById("bajas").innerHTML = myGame.player.bajas;
}
//Funcion que deja objeto del enemigo en el suelo
function dejarObjeto(x,y){
  if(mapa10x10[x][y] == 'EN'){
    if(myGame.enemigo.objetos[0].arma.nombre == 'blaster'){
      mapa10x10[x][y]='BL';
      pintaPosicion(x,y);
    }
  } else {
  if(myGame.kylo.objetos[0].arma.nombre == 'kylo_sword'){
    if(myGame.lvl == 0){
      mapa10x10[x][y]='KS';
    pintaPosicion(x,y);
    }else if(myGame.lvl == 1){
      mapa10x10[x][y]='EL';
      pintaPosicion(x,y);
    }
  } 

  }
}
function cogerObjeto(x,y){
  
  console.log("¿Quieres obtener el objeto? S/N");
  var objeto = "";
  if(mapa10x10[x][y]=='BL'){
     objeto = 'blaster';
  }else{
    if(mapa10x10[x][y]=='KS'){
      objeto = 'kylo_sword';
    } else  if(mapa10x10[x][y]=='EL'){
      objeto = 'espada laser';
    }
  }
    console.log("OBJETO ="+objeto);
    console.log("ENTRAAAAAAAAAAAAAAA 2");
    var nuevo_objeto = 0;
    var mejorado = 0;
    var encontrado_mochila = 0;
    
    console.log("Ataque arma enemigo = " + myGame.enemigo.objetos[0].arma.ataque);
    console.log("Nombre arma enemigo = " + myGame.enemigo.objetos[0].arma.nombre);

    console.log("Arma suelo = " + objeto);
    for(var t = 0; t < myGame.player.mochila.length; t++) {
      console.log("Arma mochila = " + myGame.player.mochila[t].arma.nombre);
      console.log("Ataque arma mochila = " + myGame.player.mochila[t].arma.ataque);
      if (objeto == myGame.player.mochila[t].arma.nombre){
        if (myGame.enemigo.objetos[0].arma.ataque > myGame.player.mochila[t].arma.ataque){
          myGame.player.mochila[t].arma = myGame.enemigo.objetos[0].arma;
          alert("Tu " + objeto + " ha mejorado");
          mejorado = 1;
  
        }
        encontrado_mochila = 1;
      }
    }
    console.log("IQ ALEX = " + myGame.enemigo.objetos[0].arma.ataque);
    console.log("IQ MANEL = " + myGame.player.manoizquierda.arma.ataque);
    if ((objeto == myGame.player.manoderecha.arma.nombre || objeto == myGame.player.manoizquierda.arma.nombre) && objeto == 'blaster'){
      console.log("ES BLASTER");
      if(myGame.enemigo.objetos[0].arma.ataque > myGame.player.manoizquierda.arma.ataque && objeto == myGame.player.manoizquierda.arma.nombre) {
         myGame.player.manoizquierda.arma = myGame.enemigo.objetos[0].arma;
         alert("Tu " + objeto + " ha mejorado");
         mejorado = 1;
      }else if (myGame.enemigo.objetos[0].arma.ataque > myGame.player.manoderecha.arma.ataque && objeto == myGame.player.manoderecha.arma.nombre){
        myGame.player.manoderecha.arma = myGame.enemigo.objetos[0].arma;
        alert("Tu " + objeto + " ha mejorado");
        mejorado = 1;
      }
    } else {
      if (encontrado_mochila == 0){
        nuevo_objeto = 1;
      }
    }
    if ((objeto == myGame.player.manoderecha.arma.nombre || objeto == myGame.player.manoizquierda.arma.nombre) && objeto == 'espada laser'){
         nuevo_objeto = 0;
    }
    if (nuevo_objeto == 0 && mejorado == 0){
      alert("Ya tienes este objeto!");
    }
    if (nuevo_objeto == 1){
      var respuesta = prompt("¿Quieres obtener " + objeto + "? S/N");
      if (respuesta == 'S' ){
        cObjeto = 1;
        if(objeto == 'blaster'){
          console.log("MANO IZQ name= " + myGame.player.manoizquierda.arma.nombre);
  
          if(myGame.player.manoizquierda.arma.nombre == "mano") {
            myGame.player.manoizquierda.arma = myGame.enemigo.objetos[0].arma;
          } else {
            console.log("MOCHILA");
            myGame.player.mochila[myGame.player.mochila.length].arma=myGame.enemigo.objetos[0].arma;
          }
        } else{
  
          if(objeto == 'espada_laser' || objeto == 'kylo_sword'){
            if(myGame.player.manoizquierda.arma.nombre == "mano") {
              myGame.player.manoizquierda.arma = myGame.kylo.objetos[0].arma;
            } else {
              var longitud = myGame.player.mochila.length;
              console.log(myGame.kylo.objetos[0].arma);
              myGame.player.mochila.push(myGame.kylo.objetos[0]);
            }
          }
        }
        updateStats();
    } else{
      if (respuesta == 'N' || respuesta == null || respuesta == "" ){
        cObjeto = 0;
      }
    }
    console.log("Quiere objeto"+cObjeto);
  }
}

    

//MAMAWEBO
function cambiaArma(){

  if (myGame){
  
    var objetos = [];
    for(i = 0; i < myGame.player.mochila.length; i++){
      objetos[i] = myGame.player.mochila[i].arma.nombre;
    }
    if (!Array.isArray(objetos) || !objetos.length) {
      alert("La mochila esta vacia!");
    } else {
      var mano = prompt("De que mano quieres cambiar el arma? (I/D)");
      var arma = prompt("Cual de estas armas quieres coger?: " + objetos);

      for(var i = 0; i < myGame.player.mochila.length; i++) {

        if(arma == myGame.player.mochila[i].arma.nombre) {
          
          if (mano == "I" || mano == "i"){
            //console.log("ENTRA MANO IZQ");
            if (myGame.player.manoizquierda.arma.nombre == 'mano'){
              myGame.player.manoizquierda.arma = myGame.player.mochila[i].arma;
              myGame.player.mochila.splice(i, 1);
            } else {
              //console.log("swap iz");
              var aux = myGame.player.manoizquierda.arma;
              myGame.player.manoizquierda.arma = myGame.player.mochila[i].arma;
              myGame.player.mochila[i].arma = aux;
            }
          } else if (mano == 'D' || mano == 'd'){
            //console.log("ENTRA MANO DCHA");
            if (myGame.player.manoderecha.arma.nombre == 'mano'){
              myGame.player.manoderecha.arma = myGame.player.mochila[i].arma;
              myGame.player.mochila.splice(i, 1);
            } else {
              //console.log("swap dcha");
              var aux = myGame.player.manoderecha.arma;
              myGame.player.manoderecha.arma = myGame.player.mochila[i].arma;
              myGame.player.mochila[i].arma = aux;
            }
          }
        }
      }
    }
    updateStats();
  }
}
//Funcion actualiza Minimapa
function updateMinimap(){
  for(i  = 0; i < 10; i++){

    for(j = 0; j < 10; j++){

      var div = document.getElementById("f"+i+"c"+j);
      if ((i == Px - 1 || i == Px + 1 || i == Px) && (j == Py - 1 || j == Py + 1 || j == Py)) {
        if (mapa10x10[i][j] == 'P') {
          div.style.backgroundColor = 'rgb(130, 130, 130)';
          div.style.border = "3px solid rgb(130, 130, 130)";
        } else {
          div.style.border = "3px solid rgb(215, 215, 215)";

        }
        if (mapa10x10[i][j] == 'EN') {
          div.style.backgroundColor = 'rgb(237, 108, 100)';
        }
        if (mapa10x10[i][j] == 'ENK') {
          div.style.backgroundColor = 'rgb(249, 233, 0)';
        }
        if (mapa10x10[i][j] == 'N') {
          div.style.backgroundColor = 'rgb(215, 215, 215)';
          div.style.backgroundImage= "";
        }
        if (mapa10x10[i][j] == 'BL' || mapa10x10[i][j] == 'KS'|| mapa10x10[i][j] == 'EL') {
          div.style.backgroundColor = 'rgb(0, 204, 0)';
          console.log("Aqui entra");
        }
        if (mapa10x10[i][j] == 'S') {
          div.style.backgroundColor = 'rgb(215, 215, 215)';
          /* 0 Norte, 1 Sud, 2 Este, 3 Oeste*/
          if(player.estadoPartida.direccion == 3){
            div.style.backgroundImage= "url('media/direccion_personaje/esquerra.png')";
          }else{
            if(player.estadoPartida.direccion == 2){
              div.style.backgroundImage= "url('media/direccion_personaje/dreta.png')";
            }else{
              if(player.estadoPartida.direccion == 1){
                div.style.backgroundImage= "url('media/direccion_personaje/endarrere.png')";
              }else{
                if(player.estadoPartida.direccion == 0){
                  div.style.backgroundImage= "url('media/direccion_personaje/endavant.png')";
                }
              }
            }
          }
          //div.style.backgroundColor = 'rgb(104, 143, 226)';
        }
        if (mapa10x10[i][j] == 'PE') {
          div.style.backgroundColor = 'rgb(163, 113, 66)';
        }
        if (mapa10x10[i][j] == 'PS') {
          div.style.backgroundColor = 'rgb(113, 147, 72)';
        }
      } else {
        div.style.backgroundColor = 'rgb(0, 0, 0)';
        div.style.border = '3px solid rgb(0, 0, 0)';
      }
      map.append(div);
    }
  }
}
// -------------------------------------------------------------------------- //
//Funció per actualitzar stats del personatje i objectes
function updateStats(){
  
  document.getElementById("name").innerHTML = myGame.player.nombre;
  document.getElementById("lvl").innerHTML = myGame.player.nivel;
 
  document.getElementById("health").innerHTML = myGame.player.vida;
  var arrayobjetos = [];
  for(i = 0; i < myGame.player.mochila.length; i++){
    console.log(myGame.player.mochila[i].arma.nombre);
    arrayobjetos.push(myGame.player.mochila[i].arma.nombre);
    console.log(arrayobjetos);
  }
  document.getElementById("objects").innerHTML = arrayobjetos;
  document.getElementById("derecha").innerHTML = myGame.player.manoderecha.arma.nombre;
  document.getElementById("izquierda").innerHTML = myGame.player.manoizquierda.arma.nombre;

  var ataque = 0;

  ataque += parseInt(myGame.player.manoizquierda.arma.ataque);
  ataque += parseInt(myGame.player.manoderecha.arma.ataque);


  var defensa = 0;
  defensa += parseInt(myGame.player.manoderecha.arma.defensa);
  defensa += parseInt(myGame.player.manoizquierda.arma.defensa);
  

  document.getElementById("attack").innerHTML = myGame.player.ataque+"+"+ataque;
  document.getElementById("defense").innerHTML = myGame.player.defensa+"+"+defensa;
}

// -------------------------------------------------------------------------- //
// obte valor damage en funcio de attack i defense
function tryAJAXAt(att, def,callback){
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200){
      console.log(this.responseText);
      callback (this.responseText);
    } else if (this.readyState == 4 && this.status == 404){
      console.log("error-----> 404");
    } else {
    }
  };
  xhttp.open("GET", "http://puigpedros.salleurl.edu/pwi/pac4/ataque.php?token=f5b68174-1750-4b18-811c-775891a23d29&ataque="+att+"&defensa="+def, true);
  xhttp.send();
}

// -------------------------------------------------------------------------- //
// Obte array amb totes les partides guardades
function tryAJAXSGetAll(){
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200){
      document.getElementById("responseServerGetAll").innerHTML = this.responseText;
    } else if (this.readyState == 4 && this.status == 404){
      console.log("error-----> 404");
    } else {
    }
  };
  xhttp.open("GET", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token=f5b68174-1750-4b18-811c-775891a23d29", true);
  xhttp.send();
}

// -------------------------------------------------------------------------- //
// Obte la info de la partida al slot indicat
function tryAJAXSGet(slot, callback){
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200){
      document.getElementById("info").innerHTML = "Partida carregada";
      callback(this.responseText);
    } else if (this.readyState == 4 && this.status == 404){
      document.getElementById("info").innerHTML = "No hi ha cap partida guardada en aquest slot";
    } else {
    }
  };
  xhttp.open("GET", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token=f5b68174-1750-4b18-811c-775891a23d29&slot=" + slot, true);
  xhttp.send();
}

// -------------------------------------------------------------------------- //
// Guarda la partida al slot indicat
function saveGame(slot){
  if (myGame === undefined){
    document.getElementById("info").innerHTML = "No hi ha cap partida carregada";
  }
  var aux = [];

  for(i = 0; i < 10; i++){
    for(j = 0; j < 10; j++){
      aux.push(mapa10x10[i][j]);
    }
  }
  for(i = 0; i < aux.length; i++){
    myGame.map[myGame.lvl][i] = aux[i];
  }
  var data = JSON.stringify(myGame);
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200){
      document.getElementById("info").innerHTML = "Partida guardada";
    } else if (this.readyState == 4 && this.status == 404){
      document.getElementById("info").innerHTML = "Ja tens una partida guardada en aquest slot";
    } else {
    }
  };
  xhttp.open("POST", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token=f5b68174-1750-4b18-811c-775891a23d29&slot=" + slot, true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("json=" + data);
}

// -------------------------------------------------------------------------- //
// Borra la partida del slot indicat
function deleteGame(slot){
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200){
      document.getElementById("info").innerHTML = "Partida borrada";
    } else if (this.readyState == 4 && this.status == 404){
      document.getElementById("info").innerHTML = "No hi ha cap partida en aquest slot";
    } else if (this.readyState == 4 && this.status != 200){
      document.getElementById("info").innerHTML = "No hi ha cap partida en aquest slot";
    }
  };
  xhttp.open("DELETE", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token=f5b68174-1750-4b18-811c-775891a23d29&slot=" + slot, true);
  xhttp.send();
}

// -------------------------------------------------------------------------- //
// Funncio auxiliar per pujar la partida al slot Nueva
function tryAJAXSPostNueva(){

  var myJSON = {map:[["P","P","P","P","P","P","P","P","P","P",
                     "P","N","N","N","EN","N","EN","N","P","P",
                      "P","N","P","P","N","ENK","N","P","P","P",
                      "P","EN","P","P","P","N","P","P","P","P",
                      "P","N","S","PE","PS","N","N","N","N","P",
                      "P","EN","P","P","P","P","P","P","N","P",
                      "P","N","N","P","P","N","N","N","EN","P",
                      "P","N","P","N","N","N","P","P","N","P",
                      "P","N","EN","N","P","N","EN","EN","N","P",
                      "P","P","P","P","P","P","P","P","P","P"],
                      ['P','P','P','P','P','P','P','P','P','P',
                      'P','N','N','N','P','P','N','N','N','P',
                      'P','P','P','N','N','P','PS','P','ENK','P',
                      'P','N','P','N','N','N','P','N','N','P',
                      'P','EN','N','EN','P','N','EN','N','P','P',
                      'P','P','P','N','N','P','P','P','N','P',
                      'P','N','N','P','N','P','N','N','EN','P',
                      'P','N','EN','P','EN','N','EN','P','P','P',
                      'P','P','N','N','N','EN','N','S','PE','P',
                      'P','P','P','P','P','P','P','P','P','P']],
                      lvl:0,
                      player:{nombre:"",vida:10, nivel:1, xp:0,ataque:2, defensa:2, bajas:0,manoizquierda:{arma:{nombre:"mano", ataque:0, defensa:0}}, manoderecha:{arma:{nombre:"espada laser", ataque:3, defensa:2}}, mochila:[{arma:{nombre:"baston",ataque:1, defensa:1}}], estadoPartida:{x:3,y:1,nivel:-2,direccion:3}},
                      enemigo:{ vida:10, ataque:4, defensa:3, xp:10, objetos:[{arma:{nombre:"blaster",ataque:2, defensa:0}}]},
                      kylo:{ vida:50, ataque:5, defensa:3, xp:15, objetos:[{arma:{nombre:"kylo_sword",ataque:5, defensa:1}}]}};

  var data = JSON.stringify(myJSON);

  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200){
      console.log("game saved");
    } else if (this.readyState == 4 && this.status == 404){
      console.log("error-----> 404");
    } else {
    }
  };
  xhttp.open("POST", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token=f5b68174-1750-4b18-811c-775891a23d29&slot=Nueva", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("json=" + data);
}

// -------------------------------------------------------------------------- //
// Funció auxiliar per borrar la partida al slot Nueva
function tryAJAXDeleteNueva(){
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200){
      console.log("done");
    } else if (this.readyState == 4 && this.status == 404){
      console.log("error-----> 404");
    } else {
    }
  };
  xhttp.open("DELETE", "http://puigpedros.salleurl.edu/pwi/pac4/partida.php?token=f5b68174-1750-4b18-811c-775891a23d29&slot=Nueva", true);
  xhttp.send();
}
// -------------------------------------------------------------------------- //
// Asigna el nivell i les sevevs stats pertinents
function updateLevel(){
  console.log("XP EN UPDATELEVEL "+myGame.player.xp );
  if (parseInt(myGame.player.xp) < 20){
    myGame.player.nivel = 1;
    myGame.player.ataque = 2;
    myGame.player.defensa = 2;
    myGame.enemigo.vida = parseInt(myGame.enemigo.vida) * parseInt(myGame.player.nivel);
    myGame.enemigo.ataque = 3;
    myGame.enemigo.defensa = 3;
    myGame.enemigo.xp = 10;
    updateStats();
    return;
  } else if ( parseInt(myGame.player.xp)>=20 && parseInt(myGame.player.xp)< 50 && myGame.player.nivel !=2) {
    document.getElementById("info").innerHTML= "Nivel 2"
    myGame.player.vida = 30;
    myGame.player.nivel = 2;
    myGame.player.ataque = 2;
    myGame.player.defensa = 3;
    myGame.enemigo.vida = parseInt(myGame.enemigo.vida) * parseInt(myGame.player.nivel);
    myGame.enemigo.ataque = 5;
    myGame.enemigo.defensa = 5;
    myGame.enemigo.xp = 20;
    myGame.enemigo.objetos[0].arma.ataque +=2;
    myGame.enemigo.objetos[0].arma.defensa +=2;
    updateStats();
    return;
  } else if (parseInt(myGame.player.xp)>=50 && parseInt(myGame.player.xp)< 90 && myGame.player.nivel !=3) {
    document.getElementById("info").innerHTML= "Nivel 3"
    myGame.player.vida = 60;
    myGame.player.nivel = 3;
    myGame.player.ataque = 3;
    myGame.player.defensa = 4;
    myGame.enemigo.vida = parseInt(myGame.enemigo.vida) * parseInt(myGame.player.nivel);
    myGame.enemigo.ataque = 6;
    myGame.enemigo.defensa = 6;
    myGame.enemigo.xp = 30;
    myGame.enemigo.objetos[0].arma.ataque +=3;
    myGame.enemigo.objetos[0].arma.defensa +=3;
    updateStats();
    return;
  } else if (parseInt(myGame.player.xp)>=90 && parseInt(myGame.player.xp) < 140 && myGame.player.nivel !=4) {
    document.getElementById("info").innerHTML= "Nivel 4"
    myGame.player.vida = 100;
    myGame.player.nivel = 4;
    myGame.player.ataque = 3;
    myGame.player.defensa = 5;
    myGame.enemigo.vida = parseInt(myGame.enemigo.vida) * parseInt(myGame.player.nivel);
    myGame.enemigo.ataque = 7;
    myGame.enemigo.defensa = 7;
    myGame.enemigo.xp = 40;
    myGame.enemigo.objetos[0].arma.ataque +=4;
    myGame.enemigo.objetos[0].arma.defensa +=4;
    updateStats();
    return;
  } else if(parseInt(myGame.player.xp) >= 140 && myGame.player.nivel !=5){
    document.getElementById("info").innerHTML= "Nivel 5"
    myGame.player.vida = 150;
    myGame.player.nivel = 5;
    myGame.player.ataque = 4;
    myGame.player.defensa = 6;
    myGame.enemigo.vida = parseInt(myGame.enemigo.vida) * parseInt(myGame.player.nivel);
    myGame.enemigo.ataque = 9;
    myGame.enemigo.defensa = 9;
    myGame.enemigo.xp = 60;
    if(myGame.enemigo.objetos[0].arma.nombre == 'blaster'){
      myGame.enemigo.objetos[0].arma.ataque +=5;
      myGame.enemigo.objetos[0].arma.defensa +=5;
    }

    updateStats();
    return;
  }
}
function changeMap(up){
  var aux = [];

  for(i = 0; i < 10; i++){
    for(j = 0; j < 10; j++){
      aux.push(mapa10x10[i][j]);
    }
  }
  for(i = 0; i < aux.length; i++){
    myGame.map[myGame.lvl][i] = aux[i];
  }
  myGame.lvl += up;
  if (myGame.lvl < 0){
    myGame.lvl = 0;
    return;
  }
  if (myGame.lvl >= 2){
    iniciarJuego();
    myGame.lvl = 0;
    var c = document.getElementById("visor");
    var ctx = c.getContext("2d");
    ctx.beginPath();
    ctx.rect(10, 330, 460, 70);
    ctx.fillStyle = '#ffffff';
    setTimeout(function(){
    ctx.fill();
    ctx.font = "30px Arial";
    ctx.fillStyle= "black";
    ctx.fillText("HAS GANADO!", 130, 375);
    ctx.stroke();
    },50);
    setTimeout(function(){
        newGame();
    },3000);
  return;
  }
  loadGame();
}
