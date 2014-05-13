
/*
 * GET home page.
 */

exports.index = function(req, res){
  var callbackUrl = req.query.callbackUrl;
  res.redirect(callbackUrl);
};
