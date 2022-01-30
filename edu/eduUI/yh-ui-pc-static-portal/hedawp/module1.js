function add(a,b) {
    return a+b;
}
function reduce(a,b) {
    return a-b;
}
//module.exports.add=add;
//module.exports.reduce = reduce;
module.exports ={add,reduce};//如果有多个方法这样导出