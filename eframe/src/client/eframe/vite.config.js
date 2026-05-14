import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import viteCompression from 'vite-plugin-compression'

function htmlRewritePlugin(pageRoutes) {
  return {
    name: 'html-rewrite',
    configureServer(server) {
      server.middlewares.use((req, res, next) => {
        const path = req.url?.split('?')[0]
        if (pageRoutes.includes(path)) {
          req.url = path + '.html' + (req.url.includes('?') ? '?' + req.url.split('?')[1] : '')
        }
        next()
      })
    }
  }
}

export default defineConfig({
  plugins: [
    htmlRewritePlugin(['/flexType']),
    vue(),
    viteCompression({ threshold: 500 })
  ],
  base: process.env.NODE_ENV === 'production' ? '/client/eframe/' : '/',
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    },
    extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
  },
  build: {
    rollupOptions: {
      input: {
        index: resolve(__dirname, 'index.html'),
        flexType: resolve(__dirname, 'flexType.html')
      }
    }
  },
  server: {
    proxy: {
      '/api': 'http://localhost:8080',
      '/login': 'http://localhost:8080',
      '/flexType/': 'http://localhost:8080',
      '/domain': 'http://localhost:8080'
    }
  }
})
