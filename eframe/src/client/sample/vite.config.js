import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import viteCompression from 'vite-plugin-compression'
import { resolve } from 'path'

export default defineConfig({
  plugins: [
    vue(),
    viteCompression({ threshold: 500 })
  ],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    },
    extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
  },
  base: '/client/sample',
  build: {
    outDir: '../../main/resources/client/sample',
    emptyOutDir: true,
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
      '/controller': 'http://localhost:8080'
    }
  }
})
