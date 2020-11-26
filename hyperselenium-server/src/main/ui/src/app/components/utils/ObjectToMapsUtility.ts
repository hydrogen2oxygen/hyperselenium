/**
 * Angular is not able to transform json string to a map.
 * It requires an interceptor. But I'm too lazy to even google what
 * a interceptor is. Therefore I write my own little utility.
 *
 * Come on Angular, this is your fault! Fix it!
 */
export class ObjectToMapsUtility {

  static convertObjectToMap(object:any):Map<string,string> {

    let map:Map<string,string> = new Map<string, string>();

    let keys = Object.keys(object);
    let values = Object.values(object);

    for (let index in keys) {
      let key:string = keys[index];
      let value:string = <string> values[index];
      map.set(key,value);
    }

    return map;
  }
}
