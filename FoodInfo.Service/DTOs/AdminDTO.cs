﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FoodInfo.Service.DTOs
{
    public class AdminDTO : BaseDTO
    {
        public string Username { get; set; }
        public bool IsAdmin { get; set; }
        
    }
}
