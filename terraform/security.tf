resource "aws_security_group" "app_sg" {
  name        = "finsight-app-sg"
  description = "Security group for FinSight Cloud application"
  vpc_id      = aws_vpc.finsight_vpc.id

  ingress {
    description = "HTTP"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
  description = "SSH for CI/CD deployment"
  from_port   = 22
  to_port     = 22
  protocol    = "tcp"
  cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    description = "Allow outbound traffic"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "finsight-app-sg"
  }
}

resource "aws_security_group" "database_sg" {
  name        = "finsight-database-sg"
  description = "Security group for FinSight Cloud RDS"
  vpc_id      = aws_vpc.finsight_vpc.id

  ingress {
    description     = "MySQL from application"
    from_port       = 3306
    to_port         = 3306
    protocol        = "tcp"
    security_groups = [aws_security_group.app_sg.id]
  }

  egress {
    description = "Allow outbound traffic"
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "finsight-database-sg"
  }
}