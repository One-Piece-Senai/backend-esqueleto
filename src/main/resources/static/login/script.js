document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;

    axios.post('/usuario/login', {
        email: email,
        senha: senha
    })
    .then(function(response) {
        // Login bem-sucedido
        console.log(response.data);
        // Redirecionar ou fazer outra ação
        window.location.href = '/home'; // redirecionar para a página inicial
    })
    .catch(function(error) {
        // Tratar erro de login
        const messageDiv = document.getElementById('message');
        if (error.response) {
            if (error.response.status === 401) {
                messageDiv.innerText = 'Senha incorreta.';
            } else if (error.response.status === 404) {
                messageDiv.innerText = 'Usuário não encontrado.';
            } else {
                messageDiv.innerText = 'Erro desconhecido.';
            }
        } else {
            messageDiv.innerText = 'Erro na conexão com o servidor.';
        }
    });
});
