document.addEventListener('DOMContentLoaded', () => {
    const filters = document.querySelectorAll('.filter');
    const cards = document.querySelectorAll('.food-container');
  
    filters.forEach(filter => {
      filter.addEventListener('click', () => {
        // Remove 'active' class from all filters
        filters.forEach(f => f.classList.remove('active'));
        // Add 'active' class to the clicked filter
        filter.classList.add('active');
  
        const filterValue = filter.dataset.filter;
  
        cards.forEach(card => {
          if (filterValue === 'all' || card.dataset.city === filterValue) {
            card.style.display = 'block';
          } else {
            card.style.display = 'none';
          }
        });
      });
    });
  });
  