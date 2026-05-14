import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

function htmlRewritePlugin() {
  return {
    name: 'html-rewrite',
    configureServer(server) {
      server.middlewares.use((req, res, next) => {
        const pages = ['sampleParent', 'allFieldsDomain']
        for (const page of pages) {
          if (req.url === `/${page}` || req.url === `/${page}/`) {
            req.url = `/${page}.html`
            break
          }
        }
        next()
      })
    }
  }
}

export default defineConfig({
  plugins: [vue(), htmlRewritePlugin()],
  base: process.env.NODE_ENV === 'production' ? '/client/sample' : '/',
  resolve: {
    alias: [
      { find: '@/eframe-lib', replacement: resolve(__dirname, '../eframe/src/eframe-lib') },
      { find: '@', replacement: resolve(__dirname, 'src') }
    ],
    extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
  },
  build: {
    rollupOptions: {
      input: {
        index: resolve(__dirname, 'index.html'),
        sampleParent: resolve(__dirname, 'sampleParent.html'),
        allFieldsDomain: resolve(__dirname, 'allFieldsDomain.html')
      }
    }
  },
  server: {
    proxy: {
      '/api': 'http://localhost:8080',
      '/domain': 'http://localhost:8080',
      '/sampleParent': {
        target: 'http://localhost:8080',
        bypass(req) {
          if (req.headers.accept && req.headers.accept.includes('text/html')) {
            return '/sampleParent.html'
          }
        }
      },
      '/allFieldsDomain': {
        target: 'http://localhost:8080',
        bypass(req) {
          if (req.headers.accept && req.headers.accept.includes('text/html')) {
            return '/allFieldsDomain.html'
          }
        }
      },
      '/login': 'http://localhost:8080',
      '/controller': 'http://localhost:8080',
      '/user': 'http://localhost:8080'
    }
  }
})
