resource "aws_db_subnet_group" "finsight_db_subnet_group" {
  name = "finsight-db-subnet-group"

  subnet_ids = [
    aws_subnet.private_subnet.id,
    aws_subnet.private_subnet_2.id
  ]

  tags = {
    Name = "finsight-db-subnet-group"
  }
}

resource "aws_db_instance" "finsight_db" {
  identifier = "finsight-db"

  engine         = "mysql"
  engine_version = "8.0"

  instance_class        = "db.t3.micro"
  allocated_storage     = 20
  max_allocated_storage = 20

  db_name  = "finsight_cloud"
  username = "finsight_admin"
  password = var.db_password

  db_subnet_group_name   = aws_db_subnet_group.finsight_db_subnet_group.name
  vpc_security_group_ids = [aws_security_group.database_sg.id]

  publicly_accessible = false

  skip_final_snapshot = true

  tags = {
    Name = "finsight-database"
  }
}