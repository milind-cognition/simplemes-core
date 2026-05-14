import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

function htmlRewritePlugin() {
  return {
    name: 'html-rewrite',
    configureServer(server) {
      server.middlewares.use((req, res, next) => {
        const pages = ['flexType']
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
  base: process.env.NODE_ENV === 'production' ? '/client/eframe' : '/',
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
      '/domain': 'http://localhost:8080',
      '/flexType': {
        target: 'http://localhost:8080',
        bypass(req) {
          if (req.headers.accept && req.headers.accept.includes('text/html')) {
            return '/flexType.html'
          }
        }
      },
      '/login': 'http://localhost:8080',
      '/controller': 'http://localhost:8080',
      '/user': 'http://localhost:8080'
    }
  }
})
