import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import viteCompression from 'vite-plugin-compression'

export default defineConfig({
  plugins: [
    vue(),
    viteCompression({ threshold: 500 })
  ],
  base: process.env.NODE_ENV === 'production' ? '/client/eframe/' : '/',
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
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
      '/flexType': 'http://localhost:8080',
      '/domain': 'http://localhost:8080',
      '/sampleParent': 'http://localhost:8080',
      '/allFieldsDomain': 'http://localhost:8080'
    }
  }
})
