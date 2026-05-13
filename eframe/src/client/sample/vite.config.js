import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import compression from 'vite-plugin-compression'

export default defineConfig({
  plugins: [
    vue(),
    compression({ threshold: 500 })
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  base: process.env.NODE_ENV === 'production' ? '/client/sample/' : '/',
  server: {
    proxy: {
      '/api': 'http://localhost:8080',
      '/domain': 'http://localhost:8080',
      '/sampleParent': 'http://localhost:8080',
      '/allFieldsDomain': 'http://localhost:8080'
    }
  },
  build: {
    rollupOptions: {
      input: {
        index: resolve(__dirname, 'index.html'),
        AllFieldsDomain: resolve(__dirname, 'AllFieldsDomain.html'),
        SampleParent: resolve(__dirname, 'SampleParent.html')
      }
    }
  }
})
