export const currentDate = new Intl.DateTimeFormat('pt-BR', {
  year: 'numeric',
  month: '2-digit',
  day: '2-digit',
})
  .format(new Date())
  .replace(/(\d{2})\/(\d{2})\/(\d{4})/, '$3-$2-$1');
