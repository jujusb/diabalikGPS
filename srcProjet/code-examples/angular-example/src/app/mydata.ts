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
        // The JSON class can both parse JSON from a string, a produce a string from a JSON object (stringify)
        this.storage = JSON.parse('{"type":"Player","@id":1,"name":"foo","color":{"code":"#FF0000"}}');
    }
}
