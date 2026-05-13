import pluginVue from 'eslint-plugin-vue'

export default [
  ...pluginVue.configs['flat/vue3-essential'],
  {
    rules: {
      'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
      'no-mixed-spaces-and-tabs': 'off'
    }
  }
]
