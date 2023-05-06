import { createServer } from 'node:http';

const port = 3000 || process.env.PORT;

const server = createServer((req, res) => {
  res.writeHead(200, {
    'Content-Type': 'application/json',
  });
  res.end(
    JSON.stringify({
      message: 'Hello World!',
    }),
  );
});

server.listen(port, () => {
  console.log(`Server is running on port: ${port}`);
});
