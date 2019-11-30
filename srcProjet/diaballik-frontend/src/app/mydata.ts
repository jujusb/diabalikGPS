import { Injectable } from '@angular/core';

// This class is data that will be used through the different components
// by dependency injection.
// See mycomponent.component.ts for a usage of this class.
// See app.module.ts to see how this injection is configured
@Injectable()
export class MyData {
    // The data to store
    // any: https://www.typescriptlang.org/docs/handbook/basic-types.html
    public storage: any;

    public constructor() {
        // An example of how storage can be used to store json data.
        
        //this.storage = JSON.parse('{"nbActions":0,"currentTurn":0,"gameBoard":{"undoable_mode":[],"redoable_mode":[],"board":[{"position":{"posX":0,"posY":0},"ballOwner":false,"player":"white"},{"position":{"posX":1,"posY":0},"ballOwner":false,"player":"white"},{"position":{"posX":2,"posY":0},"ballOwner":false,"player":"white"},{"position":{"posX":3,"posY":0},"ballOwner":true,"player":"white"},{"position":{"posX":4,"posY":0},"ballOwner":false,"player":"white"},{"position":{"posX":5,"posY":0},"ballOwner":false,"player":"white"},{"position":{"posX":6,"posY":0},"ballOwner":false,"player":"white"},null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,{"position":{"posX":0,"posY":6},"ballOwner":false,"player":"black"},{"position":{"posX":1,"posY":6},"ballOwner":false,"player":"black"},{"position":{"posX":2,"posY":6},"ballOwner":false,"player":"black"},{"position":{"posX":3,"posY":6},"ballOwner":true,"player":"black"},{"position":{"posX":4,"posY":6},"ballOwner":false,"player":"black"},{"position":{"posX":5,"posY":6},"ballOwner":false,"player":"black"},{"position":{"posX":6,"posY":6},"ballOwner":false,"player":"black"}]},"player1":{"type":"humanPlayer","name":"Bob","colour":true,"winner":false},"player2":{"type":"humanPlayer","name":"Alice","colour":false,"winner":false},"currentPlayer":{"type":"humanPlayer","name":"Bob","colour":true,"winner":false}}');
    }

    public receiveJson(json : string){
        // The JSON class can both parse JSON from a string, a produce a string from a JSON object (stringify)
        console.log(json);
        this.storage = JSON.parse(JSON.stringify(json));
    }
}
