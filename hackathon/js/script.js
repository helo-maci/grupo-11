  document.querySelector('input[name="cpf"]').addEventListener('input', function(e) {
    this.value = this.value.replace(/\D/g, '')
  });
  