import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import viteCompression from 'vite-plugin-compression'

export default defineConfig({
  plugins: [
    vue(),
    viteCompression({ threshold: 500 })
  ],
  base: process.env.NODE_ENV === 'production' ? '/client/sample/' : '/',
  resolve: {
    alias: [
      { find: /^@\/eframe-lib\/(.*)/, replacement: resolve(__dirname, '../eframe/src/eframe-lib/$1') },
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
      '/sampleParent': 'http://localhost:8080',
      '/allFieldsDomain': 'http://localhost:8080',
      '/domain': 'http://localhost:8080'
    }
  }
})
