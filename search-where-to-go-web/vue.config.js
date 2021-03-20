const path = require('path')

module.exports = {
  chainWebpack: config => {
    config.module
      .rule('properties-loader')
      .test(/\.properties/)
      .use('properties-loader')
      .loader('properties-loader')
      .end()
  },
  devServer: {
    contentBase: path.join(__dirname, 'config'),
    contentBasePublicPath: '/config',
    port: 8081
  }
}
