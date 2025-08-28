// Навигация
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', function(e) {
        e.preventDefault();
        
        // Убрать активный класс у всех ссылок
        document.querySelectorAll('.nav-link').forEach(l => l.classList.remove('active'));
        // Добавить активный класс текущей ссылке
        this.classList.add('active');
        
        // Скрыть все секции
        document.querySelectorAll('section').forEach(section => section.classList.remove('active-section'));
        // Показать выбранную секцию
        const sectionId = this.getAttribute('data-section');
        document.getElementById(sectionId).classList.add('active-section');
    });
});

// Логика пасхалок
const letters = ['L', 'o', 'v', 'e', ' ', 'U'];
let foundLetters = [];
let currentLetterIndex = 0;

// Создание карточек с пасхалками
function createPuzzleCards() {
    const puzzleGrid = document.getElementById('puzzleGrid');
    puzzleGrid.innerHTML = '';
    
    // Создаем 12 карточек (6 с буквами, 6 с пасхалками)
    const allItems = [...letters, '🌟', '❤️', '🎁', '🎉', '🎈', '💎'];
    
    // Перемешиваем массив
    const shuffledItems = [...allItems].sort(() => Math.random() - 0.5);
    
    shuffledItems.forEach((item, index) => {
        const card = document.createElement('div');
        card.className = 'puzzle-card';
        card.innerHTML = `<div style="font-size: 2em;">${item}</div>`;
        card.dataset.letter = item;
        card.addEventListener('click', () => revealLetter(card));
        puzzleGrid.appendChild(card);
    });
}

// Открытие карточки
function revealLetter(card) {
    if (card.classList.contains('found')) return;
    
    const letter = card.dataset.letter;
    const messageElement = document.getElementById('message');
    
    // Проверяем, является ли это правильной буквой
    if (letter === letters[currentLetterIndex]) {
        card.classList.add('found');
        foundLetters.push(letter);
        currentLetterIndex++;
        
        if (currentLetterIndex < letters.length) {
            messageElement.textContent = `Найдите букву ${letters[currentLetterIndex]}`;
        } else {
            messageElement.textContent = 'Поздравляем! Вы нашли все буквы!';
            document.getElementById('loveMessage').style.display = 'block';
        }
    } else {
        // Если это не буква, показываем пасхалку
        if (['🌟', '❤️', '🎁', '🎉', '🎈', '💎'].includes(letter)) {
            messageElement.textContent = `Пасхалка! ${letter} Найдите букву ${letters[currentLetterIndex]}`;
            setTimeout(() => {
                if (currentLetterIndex < letters.length) {
                    messageElement.textContent = `Найдите букву ${letters[currentLetterIndex]}`;
                }
            }, 2000);
        }
    }
}

// Сброс игры
document.getElementById('resetBtn').addEventListener('click', function() {
    foundLetters = [];
    currentLetterIndex = 0;
    document.getElementById('message').textContent = 'Найдите букву L';
    document.getElementById('loveMessage').style.display = 'none';
    createPuzzleCards();
});

// Секретная кнопка и модальное окно
document.getElementById('secretBtn').addEventListener('click', function() {
    document.getElementById('confessionModal').style.display = 'flex';
});

document.getElementById('closeModal').addEventListener('click', function() {
    document.getElementById('confessionModal').style.display = 'none';
});

// Закрытие модального окна при клике вне его
window.addEventListener('click', function(event) {
    const modal = document.getElementById('confessionModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

// Инициализация
createPuzzleCards();