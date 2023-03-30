
export const Utils = {
  isEmptyObj: obj => Object.keys(obj).length === 0 && obj.constructor === Object,
  cloneObj: src => {
    let target = {};
    for (let prop in src) {
      if (prop in src) {
        target[prop] = src[prop];
      }
    }
    return target;
  },
  logMessage: (message, ...params) => {
    let allParams = [message];
    params.forEach(p=>allParams.push(p));
    Reflect.apply(console.log, undefined, allParams);
  }
};
